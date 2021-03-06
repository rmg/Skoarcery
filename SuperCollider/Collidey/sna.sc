(
var bass = """
| Am |: ] ] o/ ] } ) :| G :| F :|
""".pskoar;

var mel = """
| ~a ]] c ]] d ]] c ]] d ]] e ]] :|:|:| f ] a ] o/ f ] c ) o/ f ] e ) | g ] a ] o/ g ] c ) o/ g ] e ) |
""".pskoar;

Ppar([bass, mel]).play;
)

(
var bass = """
| Am |: ] ] o/ ] } ) :| F :| G :|
""".pskoar;

var mel = """
|: a]]] c]]] d]]]  c]]] d]]] e]]]  d]]] e]]] f]]]  _a] :|

f ] a ] o/ f ] c ) o/ f ] e ) |
g ] a ] o/ g ] c ) o/ g ] e ) :|
""".pskoar;

Ppar([bass, mel]).play;
)

"| a ]]] b]]] _c ]]]  _b]]] c]]] d]]]  c]]] d]]] e]]]  d]]] e]]] _a]. o/ |".pskoar.play;

" a ] b ] c ]".pskoar.play;


(
var bass = """ o~~
| Am |: ] ] o/ ] } ) :| F :| G :|

""".pskoar;

var mel = """
| a]]] _c]]] d]]]  c]]] d]]] e]]]  d]]] e]]] f]]]  a] :|

f ] a ] o/ f ] c ) o/ f ] e ) :| g ] a ] o/ g ] c ) o/ g ] e ) :|
""".pskoar;

Ppar([bass, mel]).play;
)


SynthDef(\default, { arg freq = 440; Out.ar(0, SinOsc.ar(freq, 0, 0.2)) }).store;
(
SynthDef(\default, { arg out=0, freq=440, amp=0.1, pan=0, gate=1;
	var z;
	z = LPF.ar(
			Mix.new(VarSaw.ar(freq + [0, Rand(-0.4,0.0), Rand(0.0,0.4)], 0, 0.3, 0.3)),
			XLine.kr(Rand(4000,5000), Rand(2500,3200), 1)
		) * Linen.kr(gate, 0.01, 0.7, 0.3, 2);
	OffsetOut.ar(out, Pan2.ar(z, pan, amp));
}).store;)

(

// 28 days later
var creepy = """
@smooth => @instrument
| d ] _a ] a ] _a ] :| :| :| :| :| :| :| :| :| :| :| :|
""".pskoar;

var bass = """
| D ] ]  ] ]  ] ]  ] ] | _a# ] ]  ] ]  ] ]  ] ]  | F ] ]  ] ]  ] ]  ] ]  | G ] ]  ] ]  ] ]  ] ]  :|
""".pskoar;

var guitar = "|: c# ) ) d ) ) e ) ) f ) ) :| :| :|".pskoar;

Ppar([creepy, bass, guitar]).play;
)

(

"_b ] c ] d ]] oo/ ] c ]".pskoar.play


"killall scsynth".unixCmd;
