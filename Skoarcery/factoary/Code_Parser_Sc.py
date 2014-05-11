import unittest
from Skoarcery import langoids, terminals, nonterminals, dragonsets, parsetable
from Skoarcery.langoids import Terminal, Nonterminal
from Skoarcery.emissions import SC


class Code_Parser_Sc(unittest.TestCase):

    def setUp(self):
        terminals.init()
        nonterminals.init()
        langoids.init()
        dragonsets.init()
        parsetable.init()

    def test_sc_rdpp(self):
        from Skoarcery.dragonsets import FIRST, FOLLOW
        from Skoarcery.terminals import Empty

        fd = open("../../SuperCollider/Klassy/rdpp.sc", "w")
        SC.fd = fd

        # Header
        # Imports
        # class SkoarParseException
        # class SkoarParser:
        #     __init__
        #     fail
        self.code_start()

        SC.tab += 1
        N = nonterminals.nonterminals.values()

        # write each nonterminal as a function
        for A in N:

            R = A.production_rules

            #SC.cmt(str(A))
            SC.code_line(A.name + " {")
            SC.tab += 1
            SC.code_line("| parent |\n")

            if A.intermediate:
                SC.code_line("var noad = parent;")
            else:
                SC.code_line("var noad = SkoarNoad('" + A.name + "', None, parent);")

            SC.code_line("var desires = nil;\n")

            #SC.code_line("print('" + A.name + "')")

            for P in R:

                if P.derives_empty:
                    continue

                # A -> alpha
                alpha = P.production

                desires = FIRST(alpha)

                if Empty in desires:
                    desires.discard(Empty)
                    desires.update(FOLLOW(A))

                SC._cmt(str(P))

                i = 0

                n = len(desires)
                SC.code_line("desires = [", end="")
                for toke in desires:
                    SC.raw(toke.toker_name + ".class")
                    i += 1
                    if i != n:
                        if i % 5 == 0:
                            SC.raw(",\n")
                            SC.code_line("           ", end="")
                        else:
                            SC.raw(", ")

                else:
                    SC.raw("];\n")

                SC._if("toker.sees(desires)")

                #SC.print(str(P))

                for x in alpha:
                    if isinstance(x, Terminal):
                        SC.code_line('noad.add_toke("' + x.toker_name + '", toker.burn(' + x.toker_name + '));')

                        #SC.print("burning: " + x.name)
                    else:
                        if x.intermediate:
                            SC.code_line("this." + x.name + "(noad);")
                        else:
                            SC.code_line("noad.add_noad(this." + x.name + "(noad));")
                else:
                    SC._return("noad")

                SC.code_line("};\n")

            if A.derives_empty:
                SC._cmt("<e>")
                #SC.print("burning empty")
                SC._return("noad")

            else:
                SC._cmt("Error State")
                SC.code_line("this.fail;")
                SC.tab -= 1

            SC.code_line("}")
            SC._newline()

        SC.tab -= 1
        SC.code_line("}")

        fd.close()

    def code_start(self):

        SC._file_header("rdpp.sc", "Code_Parser_Sc - Create Recursive Descent Predictive Parser")
        SC.raw("""
SkoarParseException : Exception {

}

SkoarParser {

    var <runtime, <toker, <tab;

    *new {
        | runtime |
        ^super.new.init( runtime )
    }

    init {
        | runtime |

        runtime = runtime;
        toker = runtime.toker;
        tab = 0;
    }

    fail {
        toker.dump;
        SkoarParseException("Fail").throw;
    }

    //print {
    //    | line, end |
    //    (line ++ end).postln;
    //}

""")

