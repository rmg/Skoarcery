// ================================================================================
// lex.sc - Generated by Code_Sc_Lexer on 2014-07-23 06:07:26 for SuperCollider 3.6
// ================================================================================
// --------------
// SkoarException
// --------------
SkoarError : Exception {
    *new {
        | msg |

        ^super.new(msg);
    }

    *errorString {
        ^"SKOAR" ++ super.errorString;
    }

}

// --------------
// Abstract Token
// --------------
SkoarToke {
    var <lexeme;
    var <>val;
    var <>low;
    var <>sharps;
    var <>is_beat;
    var <>is_rest;
    var <>pre_repeat;
    var <>post_repeat;
    var <>unspent;
    classvar <regex = nil;
    classvar <inspectable = false;

    *new {
        | s |

        ^super.new.init( s );
    }

    init {
        | s |

        lexeme = s;
    }

    // how many characters to burn from the buffer
    burn {
        ^lexeme.size;
    }

    // override and return nil for no match, new toke otherwise
    *match {
        | buf, offs |

        SubclassResponsibilityError("What are you doing human?").throw;
    }

    // match requested toke
    *match_toke {
        | buf, offs, toke_class |

        var match;
        try {
            match = buf.findRegexp(toke_class.regex, offs);
            if (match[0][0] == offs) {
                ^toke_class.new(match[0][1]);
            };

        } {
            // pass
        }

        ^nil;
    }

}

// ---------------------
// Whitespace is special
// ---------------------
Toke_Whitespace : SkoarToke {
    classvar <regex = "^(\\s*)";

    *burn {
        | buf, offs |

        var match;
        try {
            match = buf.findRegexp(Toke_Whitespace.regex, offs);
            if (match[0][0] == offs) {
                ^match[0][1].size;
            };

        } {
            // pass
        }

        ^0;
    }

}

// --------------
// EOF is special
// --------------
Toke_EOF : SkoarToke {
    *burn {
        | buf, offs |

        if (buf.size > offs) {
            SkoarError("Tried to burn EOF when there's more input.").throw;
        };

        ^0;
    }

    *match {
        | buf, offs |

        if (buf.size < offs) {
            SkoarError("Tried to burn EOF when there's more input.").throw;
        };

        if (buf.size == offs) {
            ^Toke_EOF.new();
        };

        ^nil;
    }

}

// --------------
// Everyday Tokes
// --------------
Toke_QuindicesimaB : SkoarToke {
    classvar <regex = "^(15mb|alla quindicesimb)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_QuindicesimaB);
    }

}

Toke_TrebleClef : SkoarToke {
    classvar <regex = "^(G:|treble:)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_TrebleClef);
    }

}

Toke_AlSegno : SkoarToke {
    classvar <regex = "^(al segno)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AlSegno);
    }

}

Toke_Volta : SkoarToke {
    classvar <regex = "^(\\[\\d+\\.\\])";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Volta);
    }

}

Toke_ListS : SkoarToke {
    classvar <regex = "^(<(?![!=?]))";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_ListS);
    }

}

Toke_AltoClef : SkoarToke {
    classvar <regex = "^(C:|alto:)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AltoClef);
    }

}

Toke_Coda : SkoarToke {
    classvar <regex = "^(\\([+]\\))";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Coda);
    }

}

Toke_MeterS : SkoarToke {
    classvar <regex = "^(<!)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_MeterS);
    }

}

Toke_AccSharp : SkoarToke {
    classvar <regex = "^(#|sharp)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AccSharp);
    }

}

Toke_Soak : SkoarToke {
    classvar <regex = "^([?])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Soak);
    }

}

Toke_Slash : SkoarToke {
    classvar <regex = "^(/)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Slash);
    }

}

Toke_MsgOp : SkoarToke {
    classvar <regex = "^(\\.)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_MsgOp);
    }

}

Toke_PedalDown : SkoarToke {
    classvar <regex = "^(Ped\\.?)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_PedalDown);
    }

}

Toke_BooleanOp : SkoarToke {
    classvar <regex = "^(==|!=|<=|>=|in|nin|and|or|xor)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_BooleanOp);
    }

}

Toke_Comment : SkoarToke {
    classvar <regex = "^(<[?](.|[\\n\\r])*?[?]>)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Comment);
    }

}

Toke_OttavaB : SkoarToke {
    classvar <regex = "^(8vb|ottava (bassa|sotto))";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_OttavaB);
    }

}

Toke_Symbol : SkoarToke {
    classvar <regex = "^([\\\\@][a-zA-Z][a-zA-Z0-9]+)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Symbol);
    }

}

Toke_Choard : SkoarToke {
    classvar <regex = "^((D(?![a.])|[ABCEFG])([Mm0-9]|sus|dim)*)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Choard);
    }

}

Toke_Segno : SkoarToke {
    classvar <regex = "^(%S%(?:_[a-zA-Z_][a-zA-Z0-9_]*)*)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Segno);
    }

}

Toke_RWing : SkoarToke {
    classvar <regex = "^([)]\\^\\^)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_RWing);
    }

}

Toke_Tuplet : SkoarToke {
    classvar <regex = "^(/\\d+(:\\d+)?|(du|tri|quadru)plets?|(quin|sex|sep|oc)tuplets?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Tuplet);
    }

}

Toke_DynPiano : SkoarToke {
    classvar <regex = "^((mp|p+)(iano)?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DynPiano);
    }

}

Toke_AccFlat : SkoarToke {
    classvar <regex = "^(flat)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AccFlat);
    }

}

Toke_DynFP : SkoarToke {
    classvar <regex = "^(fp)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DynFP);
    }

}

Toke_AssOp : SkoarToke {
    classvar <regex = "^(=>)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AssOp);
    }

}

Toke_Carrots : SkoarToke {
    classvar <regex = "^(\\^+(?!\\^\\^[(])?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Carrots);
    }

}

Toke_Quarters : SkoarToke {
    classvar <regex = "^([)]+\\.?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Quarters);
    }

}

Toke_Quavers : SkoarToke {
    classvar <regex = "^(o+/\\.?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Quavers);
    }

}

Toke_CondS : SkoarToke {
    classvar <regex = "^([{][?])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_CondS);
    }

}

Toke_DynSFZ : SkoarToke {
    classvar <regex = "^(sfz)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DynSFZ);
    }

}

Toke_OctaveShift : SkoarToke {
    classvar <regex = "^(~+o|o~+)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_OctaveShift);
    }

}

Toke_BassClef : SkoarToke {
    classvar <regex = "^(F:|bass:)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_BassClef);
    }

}

Toke_OttavaA : SkoarToke {
    classvar <regex = "^(8va|ottava (alta|sopra)|all' ottava)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_OttavaA);
    }

}

Toke_Slur : SkoarToke {
    classvar <regex = "^([+][+])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Slur);
    }

}

Toke_CurNoat : SkoarToke {
    classvar <regex = "^([$])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_CurNoat);
    }

}

Toke_AlFine : SkoarToke {
    classvar <regex = "^(al fine)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AlFine);
    }

}

Toke_ListE : SkoarToke {
    classvar <regex = "^(>)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_ListE);
    }

}

Toke_Portamento : SkoarToke {
    classvar <regex = "^(port\\.?)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Portamento);
    }

}

Toke_String : SkoarToke {
    classvar <regex = "^('[^']*')";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_String);
    }

}

Toke_Loco : SkoarToke {
    classvar <regex = "^(loco)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Loco);
    }

}

Toke_CondE : SkoarToke {
    classvar <regex = "^([?][}])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_CondE);
    }

}

Toke_PedalUp : SkoarToke {
    classvar <regex = "^([*])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_PedalUp);
    }

}

Toke_MeterE : SkoarToke {
    classvar <regex = "^(!>)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_MeterE);
    }

}

Toke_Nosey : SkoarToke {
    classvar <regex = "^(,)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Nosey);
    }

}

Toke_Fine : SkoarToke {
    classvar <regex = "^(fine)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Fine);
    }

}

Toke_DaCapo : SkoarToke {
    classvar <regex = "^(D\\.C\\.|Da Capo)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DaCapo);
    }

}

Toke_Crotchets : SkoarToke {
    classvar <regex = "^([}]+\\.?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Crotchets);
    }

}

Toke_ListSep : SkoarToke {
    classvar <regex = "^(,)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_ListSep);
    }

}

Toke_Bars : SkoarToke {
    classvar <regex = "^(:?\\|+:?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Bars);
    }

}

Toke_AlCoda : SkoarToke {
    classvar <regex = "^(al(la)? coda)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AlCoda);
    }

}

Toke_Caesura : SkoarToke {
    classvar <regex = "^(//)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Caesura);
    }

}

Toke_DalSegno : SkoarToke {
    classvar <regex = "^(D\\.S\\.|Dal Segno)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DalSegno);
    }

}

Toke_DynForte : SkoarToke {
    classvar <regex = "^(mf(orte)?|f+orte|ff+)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DynForte);
    }

}

Toke_Eighths : SkoarToke {
    classvar <regex = "^(\\]+\\.?)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Eighths);
    }

}

Toke_MsgName : SkoarToke {
    classvar <regex = "^([a-zA-Z_][a-zA-Z0-9_]*)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_MsgName);
    }

}

Toke_DubRep : SkoarToke {
    classvar <regex = "^(/\\.\\|\\./)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_DubRep);
    }

}

Toke_MsgNameWithArgs : SkoarToke {
    classvar <regex = "^([a-zA-Z_][a-zA-Z0-9_]*<)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_MsgNameWithArgs);
    }

}

Toke_Rep : SkoarToke {
    classvar <regex = "^(\\./\\.)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Rep);
    }

}

Toke_NamedNoat : SkoarToke {
    classvar <regex = "^((?:_?)(?:[a-eg]|f(?![ac-zA-Z_]))(#*|b*))";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_NamedNoat);
    }

}

Toke_LWing : SkoarToke {
    classvar <regex = "^(\\^\\^[(])";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_LWing);
    }

}

Toke_Int : SkoarToke {
    classvar <regex = "^((-)?(0|[1-9][0-9]*)(?![mv][ab]|\\.[0-9]))";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Int);
    }

}

Toke_Float : SkoarToke {
    classvar <regex = "^((-)?(0|[1-9][0-9]*)\\.[0-9]+)";
    classvar <inspectable = true;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_Float);
    }

}

Toke_QuindicesimaA : SkoarToke {
    classvar <regex = "^(15ma|alla quindicesima)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_QuindicesimaA);
    }

}

Toke_CondSep : SkoarToke {
    classvar <regex = "^(;)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_CondSep);
    }

}

Toke_AccNatural : SkoarToke {
    classvar <regex = "^(nat)";
    classvar <inspectable = false;

    *match {
        | buf, offs |

        ^SkoarToke.match_toke(buf, offs, Toke_AccNatural);
    }

}

