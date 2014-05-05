import unittest
from Skoarcery import langoids, tokens, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import PY


class Code_Parser_Py(unittest.TestCase):

    def setUp(self):
        tokens.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()

    def test_pyrdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.tokens import Empty

        fd = open("../pymp/rdpp.py", "w")
        PY.fd = fd

        # Header
        # Imports
        # class SkoarParseException
        # class SkoarParser:
        #     __init__
        #     fail
        self.code_start()

        PY.tab += 1
        N = nonterminals.nonterminals.values()

        # write each nonterminal as a function
        for A in N:

            R = A.production_rules

            #PY.cmt(str(A))
            PY.code_line("def " + A.name + "(I, parent):")
            PY.tab += 1
            PY.code_line("I.tab += 1")
            PY.code_line("noad = SkoarNoad('" + A.name + "', None, parent) ")
            #PY.code_line("print('" + A.name + "')")

            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                desires = FIRST(alpha)

                if Empty in desires:
                    desires.discard(Empty)
                    desires.update(FOLLOW(A))

                PY.cmt(str(P))

                i = 0

                n = len(desires)
                PY.code_line("desires = [", end="")
                for toke in desires:
                    PY.code_raw(toke.toker_name)
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            PY.code_raw(",\n")
                            PY.code_line("           ", end="")
                        else:
                            PY.code_raw(", ")

                else:
                    PY.code_raw("]\n")

                PY.code_if("I.toker.sees(desires)")

                #PY.print(str(P))

                for x in alpha:
                    if isinstance(x, Terminal):
                        PY.code_line("noad.addToke('" + x.toker_name + "', I.toker.burn(" + x.toker_name + "))")

                        #PY.print("burning: " + x.name)
                    else:
                        PY.code_line("noad.addNoad(I." + x.name + "(noad))")
                else:
                    PY.code_return("noad")

            if A.derives_empty:
                PY.cmt("<e>")
                #PY.print("burning empty")
                PY.code_return()

            else:
                PY.cmt("Error State")
                PY.code_line("I.fail()")
                PY.tab -= 1

            PY.newline()

        PY.tab -= 1

        fd.close()

    def code_start(self):
        from Skoarcery.tokens import Empty

        PY.file_header("rdpp.py", "PyRDPP - Create Recursive Descent Predictive Parser")
        s = "from Skoarcery.pymp.apparatus import SkoarNoad\n"\
            "from Skoarcery.pymp.lex import "
        T = tokens.tokens.values()
        n = len(T)
        i = 0
        for t in T:
            if t == Empty:
                n -= 1
                continue
            s += t.toker_name
            i += 1
            if i != n:
                if i % 5 == 0:
                    s += ", \\\n    "
                else:
                    s += ", "

        PY.code_raw(s + """


class SkoarParseException(Exception):
    pass


class SkoarParser:

    def __init__(I, runtime):
        I.runtime = runtime
        I.toker = runtime.toker
        I.tab = 0

    def fail(I):
        I.toker.dump()
        raise SkoarParseException

    @property
    def tabby(I):
        if I.tab == 0:
            return ""

        return ("{:>" + str(I.tab * 2) + "}").format(" ")

    def print(I, line, end):
        print(I.tabby + line, end=end)


""")
