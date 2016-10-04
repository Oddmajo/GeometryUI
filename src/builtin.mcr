<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="@builtin@/3Ddode" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Expression name="E1" n="0" type="thick" hidden="super" showname="true" showvalue="true" x="9.080980804480273" y="1.805465194716793" value="(1+sqrt(5))/2" prompt="Valeur">Expression &quot;(1+sqrt(5))/2&quot; à 9.08098, 1.80547</Expression>
<Point name="O" n="1" parameter="true" mainparameter="true" x="7.473594543688671" y="0.18157868473430405">Point</Point>
<Point name="X" n="2" parameter="true" mainparameter="true" x="7.054020222400355" y="-0.08685634391435265">Point</Point>
<Point name="Y" n="3" parameter="true" mainparameter="true" x="8.381315529958929" y="0.05750041987226781">Point</Point>
<Point name="Z" n="4" parameter="true" mainparameter="true" x="7.473594543688671" y="1.136852059001179">Point</Point>
<Point name="A" n="5" parameter="true" mainparameter="true" x="7.679624996854827" y="0.37913591136428465">Point</Point>
<Expression name="E2" n="6" type="thick" showname="true" showvalue="true" target="true" x="x(A)" y="y(A)+15/pixel" value="1" prompt="k" fixed="true">Expression &quot;1&quot; à 7.67962, 0.46849</Expression>
<Point name="P21" n="7" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)+1/ph*k)*(x(Y)-x(O))+(z3D(A)+ph*k)*(x(Z)-x(O))" actx="8.240627418671423" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)+1/ph*k)*(y(Y)-y(O))+(z3D(A)+ph*k)*(y(Z)-y(O))" acty="1.8481161145260374" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.024624135020383697" y3D="y3D(A)+1/E1*E2" acty3D="0.8336275482111279" z3D="z3D(A)+E1*E2" actz3D="1.8459244825897214" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="8" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)-1/ph*k)*(x(Y)-x(O))+(z3D(A)+ph*k)*(x(Z)-x(O))" actx="7.118622575038231" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)-1/ph*k)*(y(Y)-y(O))+(z3D(A)+ph*k)*(y(Z)-y(O))" acty="2.001485284425738" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.024624135020383697" y3D="y3D(A)-1/E1*E2" acty3D="-0.40244042928866164" z3D="z3D(A)+E1*E2" actz3D="1.8459244825897214" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="9" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)-1/ph*k)*(x(Y)-x(O))+(z3D(A)-ph*k)*(x(Z)-x(O))" actx="7.118622575038231" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)-1/ph*k)*(y(Y)-y(O))+(z3D(A)-ph*k)*(y(Z)-y(O))" acty="-1.0898442917974682" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.024624135020383697" y3D="y3D(A)-1/E1*E2" acty3D="-0.40244042928866164" z3D="z3D(A)-E1*E2" actz3D="-1.3901434949100684" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="10" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)+1/ph*k)*(x(Y)-x(O))+(z3D(A)-ph*k)*(x(Z)-x(O))" actx="8.240627418671423" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)+1/ph*k)*(y(Y)-y(O))+(z3D(A)-ph*k)*(y(Z)-y(O))" acty="-1.2432134616971684" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.024624135020383697" y3D="y3D(A)+1/E1*E2" acty3D="0.8336275482111279" z3D="z3D(A)-E1*E2" actz3D="-1.3901434949100684" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="11" target="true" x="x(O)+(x3D(A)+1/ph*k)*(x(X)-x(O))+(y3D(A)+ph*k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="8.889037213578833" y="y(O)+(x3D(A)+1/ph*k)*(y(X)-y(O))+(y3D(A)+ph*k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.012471090076476676" shape="dcross" is3D="true" x3D="x3D(A)+1/E1*E2" actx3D="0.593409853729511" y3D="y3D(A)+E1*E2" acty3D="1.833627548211128" z3D="z3D(A)" actz3D="0.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="12" target="true" x="x(O)+(x3D(A)-1/ph*k)*(x(X)-x(O))+(y3D(A)+ph*k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="9.407659596304528" y="y(O)+(x3D(A)-1/ph*k)*(y(X)-y(O))+(y3D(A)+ph*k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.34427503302831985" shape="dcross" is3D="true" x3D="x3D(A)-1/E1*E2" actx3D="-0.6426581237702785" y3D="y3D(A)+E1*E2" acty3D="1.833627548211128" z3D="z3D(A)" actz3D="0.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="13" target="true" x="x(O)+(x3D(A)-1/ph*k)*(x(X)-x(O))+(y3D(A)-ph*k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="6.4702127801308205" y="y(O)+(x3D(A)-1/ph*k)*(y(X)-y(O))+(y3D(A)-ph*k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.7458007326520927" shape="dcross" is3D="true" x3D="x3D(A)-1/E1*E2" actx3D="-0.6426581237702785" y3D="y3D(A)-E1*E2" acty3D="-1.4024404292886619" z3D="z3D(A)" actz3D="0.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="14" target="true" x="x(O)+(x3D(A)+1/ph*k)*(x(X)-x(O))+(y3D(A)-ph*k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="5.951590397405125" y="y(O)+(x3D(A)+1/ph*k)*(y(X)-y(O))+(y3D(A)-ph*k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.41399678970024956" shape="dcross" is3D="true" x3D="x3D(A)+1/E1*E2" actx3D="0.593409853729511" y3D="y3D(A)-E1*E2" acty3D="-1.4024404292886619" z3D="z3D(A)" actz3D="0.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="15" target="true" x="x(O)+(x3D(A)+ph*k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+1/ph*k)*(x(Z)-x(O))" actx="7.000739484203663" y="y(O)+(x3D(A)+ph*k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+1/ph*k)*(y(Z)-y(O))" acty="0.5351903250844341" shape="dcross" is3D="true" x3D="x3D(A)+E1*E2" actx3D="1.5934098537295112" y3D="y3D(A)" acty3D="0.21559355946123313" z3D="z3D(A)+1/E1*E2" actz3D="0.8459244825897213" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="16" target="true" x="x(O)+(x3D(A)+ph*k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-1/ph*k)*(x(Z)-x(O))" actx="7.000739484203663" y="y(O)+(x3D(A)+ph*k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-1/ph*k)*(y(Z)-y(O))" acty="-0.6455925026050215" shape="dcross" is3D="true" x3D="x3D(A)+E1*E2" actx3D="1.5934098537295112" y3D="y3D(A)" acty3D="0.21559355946123313" z3D="z3D(A)-1/E1*E2" actz3D="-0.3901434949100683" fixed="true" fixed3D="true">Point</Point>
<Point name="P10" n="17" target="true" x="x(O)+(x3D(A)-ph*k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-1/ph*k)*(x(Z)-x(O))" actx="8.35851050950599" y="y(O)+(x3D(A)-ph*k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-1/ph*k)*(y(Z)-y(O))" acty="0.2230814976441352" shape="dcross" is3D="true" x3D="x3D(A)-E1*E2" actx3D="-1.6426581237702786" y3D="y3D(A)" acty3D="0.21559355946123313" z3D="z3D(A)-1/E1*E2" actz3D="-0.3901434949100683" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="18" target="true" x="x(O)+(x3D(A)-ph*k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+1/ph*k)*(x(Z)-x(O))" actx="8.35851050950599" y="y(O)+(x3D(A)-ph*k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+1/ph*k)*(y(Z)-y(O))" acty="1.4038643253335907" shape="dcross" is3D="true" x3D="x3D(A)-E1*E2" actx3D="-1.6426581237702786" y3D="y3D(A)" acty3D="0.21559355946123313" z3D="z3D(A)+1/E1*E2" actz3D="0.8459244825897213" fixed="true" fixed3D="true">Point</Point>
<Point name="P12" n="19" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="8.167771661836769" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="0.9418959921204667" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9753758649796163" y3D="y3D(A)+E2" acty3D="1.215593559461233" z3D="z3D(A)+E2" actz3D="1.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P13" n="20" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="9.0069203044134" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.4787660494177801" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0246241350203837" y3D="y3D(A)+E2" acty3D="1.215593559461233" z3D="z3D(A)+E2" actz3D="1.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P14" n="21" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="6.352329689296253" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.1900525218445392" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9753758649796163" y3D="y3D(A)-E2" acty3D="-0.7844064405387668" z3D="z3D(A)+E2" actz3D="1.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P15" n="22" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="6.352329689296253" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.7204942266892108" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9753758649796163" y3D="y3D(A)-E2" acty3D="-0.7844064405387668" z3D="z3D(A)-E2" actz3D="-0.7721095061601735" fixed="true" fixed3D="true">Point</Point>
<Point name="P16" n="23" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="8.167771661836769" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.9686507564132832" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9753758649796163" y3D="y3D(A)+E2" acty3D="1.215593559461233" z3D="z3D(A)-E2" actz3D="-0.7721095061601735" fixed="true" fixed3D="true">Point</Point>
<Point name="P17" n="24" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="7.191478331872885" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.7269225791418525" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0246241350203837" y3D="y3D(A)-E2" acty3D="-0.7844064405387668" z3D="z3D(A)+E2" actz3D="1.2278904938398265" fixed="true" fixed3D="true">Point</Point>
<Point name="P18" n="25" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="7.191478331872885" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.1836241693918974" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0246241350203837" y3D="y3D(A)-E2" acty3D="-0.7844064405387668" z3D="z3D(A)-E2" actz3D="-0.7721095061601735" fixed="true" fixed3D="true">Point</Point>
<Point name="P19" n="26" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="9.0069203044134" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.43178069911596983" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0246241350203837" y3D="y3D(A)+E2" acty3D="1.215593559461233" z3D="z3D(A)-E2" actz3D="-0.7721095061601735" fixed="true" fixed3D="true">Point</Point>
<Segment name="s1" n="27" color="4" target="true" ctag0="thin" cexpr0="((a(P13,P5,P4)&gt;180)&amp;&amp;(a(P4,P5,P19)&gt;180))" from="P5" to="P4" is3D="true">Segment de P5 à P4</Segment>
<Segment name="s2" n="28" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P5,P19)&gt;180)&amp;&amp;(a(P19,P5,P13)&gt;180))" from="P5" to="P19" is3D="true">Segment de P5 à P19</Segment>
<Segment name="s3" n="29" color="4" target="true" ctag0="thin" cexpr0="((a(P19,P5,P13)&gt;180)&amp;&amp;(a(P13,P5,P4)&gt;180))" from="P5" to="P13" is3D="true">Segment de P5 à P13</Segment>
<Segment name="s4" n="30" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P19,P3)&gt;180)&amp;&amp;(a(P3,P19,P10)&gt;180))" from="P19" to="P3" is3D="true">Segment de P19 à P3</Segment>
<Segment name="s5" n="31" color="4" target="true" ctag0="thin" cexpr0="((a(P19,P3,P16)&gt;180)&amp;&amp;(a(P16,P3,P2)&gt;180))" from="P3" to="P16" is3D="true">Segment de P3 à P16</Segment>
<Segment name="s6" n="32" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P16,P4)&gt;180)&amp;&amp;(a(P4,P16,P9)&gt;180))" from="P16" to="P4" is3D="true">Segment de P16 à P4</Segment>
<Segment name="s7" n="33" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P19,P10)&gt;180)&amp;&amp;(a(P10,P19,P5)&gt;180))" from="P19" to="P10" is3D="true">Segment de P19 à P10</Segment>
<Segment name="s8" n="34" color="4" target="true" ctag0="thin" cexpr0="((a(P18,P10,P11)&gt;180)&amp;&amp;(a(P11,P10,P19)&gt;180))" from="P10" to="P11" is3D="true">Segment de P10 à P11</Segment>
<Segment name="s9" n="35" color="4" target="true" ctag0="thin" cexpr0="((a(P17,P11,P13)&gt;180)&amp;&amp;(a(P13,P11,P10)&gt;180))" from="P11" to="P13" is3D="true">Segment de P11 à P13</Segment>
<Segment name="s10" n="36" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P11,P17)&gt;180)&amp;&amp;(a(P17,P11,P13)&gt;180))" from="P11" to="P17" is3D="true">Segment de P11 à P17</Segment>
<Segment name="s11" n="37" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P4,P12)&gt;180)&amp;&amp;(a(P12,P4,P16)&gt;180))" from="P4" to="P12" is3D="true">Segment de P4 à P12</Segment>
<Segment name="s12" n="38" color="4" target="true" ctag0="thin" cexpr0="((a(P11,P13,P21)&gt;180)&amp;&amp;(a(P21,P13,P5)&gt;180))" from="P13" to="P21" is3D="true">Segment de P13 à P21</Segment>
<Segment name="s13" n="39" color="4" target="true" ctag0="thin" cexpr0="((a(P19,P10,P18)&gt;180)&amp;&amp;(a(P18,P10,P11)&gt;180))" from="P10" to="P18" is3D="true">Segment de P10 à P18</Segment>
<Segment name="s14" n="40" color="4" target="true" ctag0="thin" cexpr0="((a(P16,P3,P2)&gt;180)&amp;&amp;(a(P2,P3,P19)&gt;180))" from="P3" to="P2" is3D="true">Segment de P3 à P2</Segment>
<Segment name="s15" n="41" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P18,P2)&gt;180)&amp;&amp;(a(P2,P18,P6)&gt;180))" from="P18" to="P2" is3D="true">Segment de P18 à P2</Segment>
<Segment name="s16" n="42" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P18,P6)&gt;180)&amp;&amp;(a(P6,P18,P10)&gt;180))" from="P18" to="P6" is3D="true">Segment de P18 à P6</Segment>
<Segment name="s17" n="43" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P2,P15)&gt;180)&amp;&amp;(a(P15,P2,P18)&gt;180))" from="P2" to="P15" is3D="true">Segment de P2 à P15</Segment>
<Segment name="s18" n="44" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P16,P9)&gt;180)&amp;&amp;(a(P9,P16,P3)&gt;180))" from="P16" to="P9" is3D="true">Segment de P16 à P9</Segment>
<Segment name="s19" n="45" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P6,P17)&gt;180)&amp;&amp;(a(P17,P6,P18)&gt;180))" from="P6" to="P17" is3D="true">Segment de P6 à P17</Segment>
<Segment name="s20" n="46" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P21,P12)&gt;180)&amp;&amp;(a(P12,P21,P17)&gt;180))" from="P21" to="P12" is3D="true">Segment de P21 à P12</Segment>
<Segment name="s21" n="47" color="4" target="true" ctag0="thin" cexpr0="((a(P13,P21,P1)&gt;180)&amp;&amp;(a(P1,P21,P12)&gt;180))" from="P21" to="P1" is3D="true">Segment de P21 à P1</Segment>
<Segment name="s22" n="48" color="4" target="true" ctag0="thin" cexpr0="((a(P9,P15,P7)&gt;180)&amp;&amp;(a(P7,P15,P6)&gt;180))" from="P15" to="P7" is3D="true">Segment de P15 à P7</Segment>
<Segment name="s23" n="49" color="4" target="true" ctag0="thin" cexpr0="((a(P18,P6,P7)&gt;180)&amp;&amp;(a(P7,P6,P17)&gt;180))" from="P6" to="P7" is3D="true">Segment de P6 à P7</Segment>
<Segment name="s24" n="50" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P15,P9)&gt;180)&amp;&amp;(a(P9,P15,P7)&gt;180))" from="P15" to="P9" is3D="true">Segment de P15 à P9</Segment>
<Segment name="s25" n="51" color="4" target="true" ctag0="thin" cexpr0="((a(P16,P9,P8)&gt;180)&amp;&amp;(a(P8,P9,P15)&gt;180))" from="P9" to="P8" is3D="true">Segment de P9 à P8</Segment>
<Segment name="s26" n="52" color="4" target="true" ctag0="thin" cexpr0="((a(P15,P7,P14)&gt;180)&amp;&amp;(a(P14,P7,P6)&gt;180))" from="P7" to="P14" is3D="true">Segment de P7 à P14</Segment>
<Segment name="s27" n="53" color="4" target="true" ctag0="thin" cexpr0="((a(P21,P12,P8)&gt;180)&amp;&amp;(a(P8,P12,P4)&gt;180))" from="P12" to="P8" is3D="true">Segment de P12 à P8</Segment>
<Segment name="s28" n="54" color="4" target="true" ctag0="thin" cexpr0="((a(P12,P8,P14)&gt;180)&amp;&amp;(a(P14,P8,P9)&gt;180))" from="P8" to="P14" is3D="true">Segment de P8 à P14</Segment>
<Segment name="s29" n="55" color="4" target="true" ctag0="thin" cexpr0="((a(P8,P14,P1)&gt;180)&amp;&amp;(a(P1,P14,P7)&gt;180))" from="P14" to="P1" is3D="true">Segment de P14 à P1</Segment>
<Segment name="s30" n="56" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P17,P1)&gt;180)&amp;&amp;(a(P1,P17,P11)&gt;180))" from="P17" to="P1" is3D="true">Segment de P17 à P1</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dtetra" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.5644768010115983" y="0.30680779337393893">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.2419080134156577" y="0.014094031594516998">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.155867983784152" y="-0.09231877352244827">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.5644768010115983" y="1.175724726839068">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="1.5851177945738388" y="0.9088658252146664">Point</Point>
<Expression name="E1" n="5" type="thick" showname="true" showvalue="true" target="true" x="x(A)" y="y(A)+15/pixel" value="1" prompt="k" fixed="true">Expression &quot;1&quot; à 1.58512, 1.00354</Expression>
<Point name="P5" n="6" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="1.5851177945738388" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.7777827586797956" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-1.0823958965777292" y3D="y3D(A)" acty3D="0.2499418044594575" z3D="z3D(A)+E1" actz3D="1.4430622269281528" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="7" target="true" x="x(O)+(x3D(A)+k*2*sqrt(2)/3)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-k/3)*(x(Z)-x(O))" actx="0.8248509005373473" y="y(O)+(x3D(A)+k*2*sqrt(2)/3)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-k/3)*(y(Z)-y(O))" acty="0.3432536661918195" shape="dcross" is3D="true" x3D="x3D(A)+E1*2*sqrt(2)/3" actx3D="-0.1395868549956657" y3D="y3D(A)" acty3D="0.2499418044594575" z3D="z3D(A)-E1/3" actz3D="0.10972889359481947" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="8" target="true" x="x(O)+(x3D(A)-k*sqrt(2)/3)*(x(X)-x(O))+(y3D(A)+k*sqrt(6)/3)*(x(Y)-x(O))+(z3D(A)-k/3)*(x(Z)-x(O))" actx="2.6381868438258014" y="y(O)+(x3D(A)-k*sqrt(2)/3)*(y(X)-y(O))+(y3D(A)+k*sqrt(6)/3)*(y(Y)-y(O))+(z3D(A)-k/3)*(y(Z)-y(O))" acty="0.5003212560654883" shape="dcross" is3D="true" x3D="x3D(A)-E1*sqrt(2)/3" actx3D="-1.7895026777642769" y3D="y3D(A)+E1*sqrt(6)/3" acty3D="1.0664383853871835" z3D="z3D(A)-E1/3" actz3D="0.10972889359481947" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="9" target="true" x="x(O)+(x3D(A)-k*sqrt(2)/3)*(x(X)-x(O))+(y3D(A)-k*sqrt(6)/3)*(x(Y)-x(O))+(z3D(A)-k/3)*(x(Z)-x(O))" actx="1.4823823628674901" y="y(O)+(x3D(A)-k*sqrt(2)/3)*(y(X)-y(O))+(y3D(A)-k*sqrt(6)/3)*(y(Y)-y(O))+(z3D(A)-k/3)*(y(Z)-y(O))" acty="1.0830989152218469" shape="dcross" is3D="true" x3D="x3D(A)-E1*sqrt(2)/3" actx3D="-1.553800417368761" y3D="y3D(A)-E1*sqrt(6)/3" acty3D="-0.5665547764682685" z3D="z3D(A)-E1/3" actz3D="0.10972889359481947" fixed="true" fixed3D="true">Point</Point>
<Segment name="s1" n="10" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P3,P2)&gt;180)&amp;&amp;(a(P2,P3,P1)&gt;180))" from="P3" to="P2" is3D="true">Segment de P3 à P2</Segment>
<Segment name="s2" n="11" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P1,P2)&gt;180)&amp;&amp;(a(P2,P1,P5)&gt;180))" from="P1" to="P2" is3D="true">Segment de P1 à P2</Segment>
<Segment name="s3" n="12" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P5,P2)&gt;180)&amp;&amp;(a(P2,P5,P3)&gt;180))" from="P5" to="P2" is3D="true">Segment de P5 à P2</Segment>
<Segment name="s4" n="13" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P1,P5)&gt;180)&amp;&amp;(a(P5,P1,P3)&gt;180))" from="P1" to="P5" is3D="true">Segment de P1 à P5</Segment>
<Segment name="s5" n="14" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P1,P3)&gt;180)&amp;&amp;(a(P3,P1,P2)&gt;180))" from="P1" to="P3" is3D="true">Segment de P1 à P3</Segment>
<Segment name="s6" n="15" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P3,P5)&gt;180)&amp;&amp;(a(P5,P3,P2)&gt;180))" from="P3" to="P5" is3D="true">Segment de P3 à P5</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Darete" showduplicates="true">
<Parameter name="D">D</Parameter>
<Parameter name="E">E</Parameter>
<Parameter name="F">F</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="B" n="0" mainparameter="true" x="57.39830561238021" y="21.248404810689276">Point à 57.39831, 21.2484</Point>
<Point name="E" n="1" mainparameter="true" x="57.39830561238021" y="25.150349928536837">Point à 57.39831, 25.15035</Point>
<Point name="D" n="2" mainparameter="true" x="55.04321691709174" y="25.861847914336575">Point à 55.04322, 25.86185</Point>
<Point name="F" n="3" mainparameter="true" x="60.63150174582529" y="25.668611362089926">Point à 60.6315, 25.66861</Point>
<Segment name="s1" n="4" color="1" target="true" ctag0="thin" cexpr0="((a(D,E,B)&gt;180)&amp;&amp;(a(B,E,F)&gt;180))" from="E" to="B">Segment de E à B</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcube" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="6.822714463918755" y="-0.5983401797594166">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="6.401745038039364" y="-0.8330592600150705">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="7.729789291303341" y="-0.70727226945599">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="6.822714463918755" y="0.36760016684500885">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="7.631337045185033" y="0.42322317996556147">Point</Point>
<Expression name="E1" n="5" type="thick" showname="true" showvalue="true" target="true" x="x(A)" y="y(A)+15/pixel" value="1" prompt="k" fixed="true">Expression &quot;1&quot; à 7.63134, 0.50283</Expression>
<Point name="P9" n="6" target="true" x="x(O)+(x3D(A)+k/2)*(x(X)-x(O))+(y3D(A)+k/2)*(x(Y)-x(O))+(z3D(A)+k/2)*(x(Z)-x(O))" actx="7.874389745937629" y="y(O)+(x3D(A)+k/2)*(y(X)-y(O))+(y3D(A)+k/2)*(y(Y)-y(O))+(z3D(A)+k/2)*(y(Z)-y(O))" acty="0.7343677682916604" shape="dcross" is3D="true" x3D="x3D(A)+E1/2" actx3D="0.2007764085174602" y3D="y3D(A)+E1/2" acty3D="1.2525934764595408" z3D="z3D(A)+E1/2" actz3D="1.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="7" target="true" x="x(O)+(x3D(A)+k/2)*(x(X)-x(O))+(y3D(A)-k/2)*(x(Y)-x(O))+(z3D(A)+k/2)*(x(Z)-x(O))" actx="6.967314918553043" y="y(O)+(x3D(A)+k/2)*(y(X)-y(O))+(y3D(A)-k/2)*(y(Y)-y(O))+(z3D(A)+k/2)*(y(Z)-y(O))" acty="0.8432998579882338" shape="dcross" is3D="true" x3D="x3D(A)+E1/2" actx3D="0.2007764085174602" y3D="y3D(A)-E1/2" acty3D="0.2525934764595408" z3D="z3D(A)+E1/2" actz3D="1.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="8" target="true" x="x(O)+(x3D(A)-k/2)*(x(X)-x(O))+(y3D(A)-k/2)*(x(Y)-x(O))+(z3D(A)+k/2)*(x(Z)-x(O))" actx="7.388284344432435" y="y(O)+(x3D(A)-k/2)*(y(X)-y(O))+(y3D(A)-k/2)*(y(Y)-y(O))+(z3D(A)+k/2)*(y(Z)-y(O))" acty="1.0780189382438878" shape="dcross" is3D="true" x3D="x3D(A)-E1/2" actx3D="-0.7992235914825399" y3D="y3D(A)-E1/2" acty3D="0.2525934764595408" z3D="z3D(A)+E1/2" actz3D="1.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="9" target="true" x="x(O)+(x3D(A)-k/2)*(x(X)-x(O))+(y3D(A)+k/2)*(x(Y)-x(O))+(z3D(A)+k/2)*(x(Z)-x(O))" actx="8.29535917181702" y="y(O)+(x3D(A)-k/2)*(y(X)-y(O))+(y3D(A)+k/2)*(y(Y)-y(O))+(z3D(A)+k/2)*(y(Z)-y(O))" acty="0.9690868485473144" shape="dcross" is3D="true" x3D="x3D(A)-E1/2" actx3D="-0.7992235914825399" y3D="y3D(A)+E1/2" acty3D="1.2525934764595408" z3D="z3D(A)+E1/2" actz3D="1.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="10" target="true" x="x(O)+(x3D(A)+k/2)*(x(X)-x(O))+(y3D(A)+k/2)*(x(Y)-x(O))+(z3D(A)-k/2)*(x(Z)-x(O))" actx="7.874389745937629" y="y(O)+(x3D(A)+k/2)*(y(X)-y(O))+(y3D(A)+k/2)*(y(Y)-y(O))+(z3D(A)-k/2)*(y(Z)-y(O))" acty="-0.2315725783127649" shape="dcross" is3D="true" x3D="x3D(A)+E1/2" actx3D="0.2007764085174602" y3D="y3D(A)+E1/2" acty3D="1.2525934764595408" z3D="z3D(A)-E1/2" actz3D="0.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="11" target="true" x="x(O)+(x3D(A)+k/2)*(x(X)-x(O))+(y3D(A)-k/2)*(x(Y)-x(O))+(z3D(A)-k/2)*(x(Z)-x(O))" actx="6.967314918553043" y="y(O)+(x3D(A)+k/2)*(y(X)-y(O))+(y3D(A)-k/2)*(y(Y)-y(O))+(z3D(A)-k/2)*(y(Z)-y(O))" acty="-0.12264048861619148" shape="dcross" is3D="true" x3D="x3D(A)+E1/2" actx3D="0.2007764085174602" y3D="y3D(A)-E1/2" acty3D="0.2525934764595408" z3D="z3D(A)-E1/2" actz3D="0.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="12" target="true" x="x(O)+(x3D(A)-k/2)*(x(X)-x(O))+(y3D(A)-k/2)*(x(Y)-x(O))+(z3D(A)-k/2)*(x(Z)-x(O))" actx="7.388284344432435" y="y(O)+(x3D(A)-k/2)*(y(X)-y(O))+(y3D(A)-k/2)*(y(Y)-y(O))+(z3D(A)-k/2)*(y(Z)-y(O))" acty="0.11207859163946254" shape="dcross" is3D="true" x3D="x3D(A)-E1/2" actx3D="-0.7992235914825399" y3D="y3D(A)-E1/2" acty3D="0.2525934764595408" z3D="z3D(A)-E1/2" actz3D="0.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="13" target="true" x="x(O)+(x3D(A)-k/2)*(x(X)-x(O))+(y3D(A)+k/2)*(x(Y)-x(O))+(z3D(A)-k/2)*(x(Z)-x(O))" actx="8.29535917181702" y="y(O)+(x3D(A)-k/2)*(y(X)-y(O))+(y3D(A)+k/2)*(y(Y)-y(O))+(z3D(A)-k/2)*(y(Z)-y(O))" acty="0.003146501942889124" shape="dcross" is3D="true" x3D="x3D(A)-E1/2" actx3D="-0.7992235914825399" y3D="y3D(A)+E1/2" acty3D="1.2525934764595408" z3D="z3D(A)-E1/2" actz3D="0.5697466538762503" fixed="true" fixed3D="true">Point</Point>
<Segment name="s1" n="14" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P4,P9)&gt;180)&amp;&amp;(a(P9,P4,P5)&gt;180))" from="P4" to="P9" is3D="true">Segment de P4 à P9</Segment>
<Segment name="s2" n="15" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P7,P3)&gt;180)&amp;&amp;(a(P3,P7,P4)&gt;180))" from="P7" to="P3" is3D="true">Segment de P7 à P3</Segment>
<Segment name="s3" n="16" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P6,P2)&gt;180)&amp;&amp;(a(P2,P6,P7)&gt;180))" from="P6" to="P2" is3D="true">Segment de P6 à P2</Segment>
<Segment name="s4" n="17" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P5,P1)&gt;180)&amp;&amp;(a(P1,P5,P6)&gt;180))" from="P5" to="P1" is3D="true">Segment de P5 à P1</Segment>
<Segment name="s5" n="18" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P1,P9)&gt;180)&amp;&amp;(a(P9,P1,P2)&gt;180))" from="P1" to="P9" is3D="true">Segment de P1 à P9</Segment>
<Segment name="s6" n="19" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P9,P3)&gt;180)&amp;&amp;(a(P3,P9,P1)&gt;180))" from="P9" to="P3" is3D="true">Segment de P9 à P3</Segment>
<Segment name="s7" n="20" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P3,P2)&gt;180)&amp;&amp;(a(P2,P3,P9)&gt;180))" from="P3" to="P2" is3D="true">Segment de P3 à P2</Segment>
<Segment name="s8" n="21" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P2,P1)&gt;180)&amp;&amp;(a(P1,P2,P3)&gt;180))" from="P2" to="P1" is3D="true">Segment de P2 à P1</Segment>
<Segment name="s9" n="22" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P7,P6)&gt;180)&amp;&amp;(a(P6,P7,P3)&gt;180))" from="P7" to="P6" is3D="true">Segment de P7 à P6</Segment>
<Segment name="s10" n="23" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P6,P5)&gt;180)&amp;&amp;(a(P5,P6,P2)&gt;180))" from="P6" to="P5" is3D="true">Segment de P6 à P5</Segment>
<Segment name="s11" n="24" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P5,P4)&gt;180)&amp;&amp;(a(P4,P5,P1)&gt;180))" from="P5" to="P4" is3D="true">Segment de P5 à P4</Segment>
<Segment name="s12" n="25" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P4,P7)&gt;180)&amp;&amp;(a(P7,P4,P9)&gt;180))" from="P4" to="P7" is3D="true">Segment de P4 à P7</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Docta" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="-10.34415018138828" y="0.4173812718227057">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-11.006256279259297" y="0.11234211714720299">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="-9.594740069925928" y="0.14787825697018003">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="-10.34415018138828" y="1.3307920442029388">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="-10.462361640201255" y="-0.023846279703593765">Point</Point>
<Expression name="E1" n="5" type="thick" showname="true" showvalue="true" target="true" x="x(A)" y="y(A)+15/pixel" value="1" prompt="k" fixed="true">Expression &quot;1&quot; à -10.46236, 0.03579</Expression>
<Point name="P7" n="6" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="-11.124467738072273" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="-0.3288854343790965" shape="dcross" is3D="true" x3D="x3D(A)+E1" actx3D="1.6714151357132212" y3D="y3D(A)" acty3D="0.435457957871493" z3D="z3D(A)" actz3D="-0.1303492549073485" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="7" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="-9.712951528738905" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="-0.2933492945561194" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="0.6714151357132211" y3D="y3D(A)+E1" acty3D="1.435457957871493" z3D="z3D(A)" actz3D="-0.1303492549073485" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="8" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="-9.800255542330238" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.281192874971909" shape="dcross" is3D="true" x3D="x3D(A)-E1" actx3D="-0.3285848642867789" y3D="y3D(A)" acty3D="0.435457957871493" z3D="z3D(A)" actz3D="-0.1303492549073485" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="9" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="-11.211771751663608" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.24565673514893194" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="0.6714151357132211" y3D="y3D(A)-E1" acty3D="-0.564542042128507" z3D="z3D(A)" actz3D="-0.1303492549073485" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="10" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="-10.462361640201255" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="0.8895644926766394" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="0.6714151357132211" y3D="y3D(A)" acty3D="0.435457957871493" z3D="z3D(A)+E1" actz3D="0.8696507450926515" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="11" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="-10.462361640201255" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.9372570520838268" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="0.6714151357132211" y3D="y3D(A)" acty3D="0.435457957871493" z3D="z3D(A)-E1" actz3D="-1.1303492549073484" fixed="true" fixed3D="true">Point</Point>
<Segment name="s1" n="12" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P7,P4)&gt;180)&amp;&amp;(a(P4,P7,P3)&gt;180))" from="P7" to="P4">Segment de P7 à P4</Segment>
<Segment name="s2" n="13" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P3,P4)&gt;180)&amp;&amp;(a(P4,P3,P2)&gt;180))" from="P3" to="P4">Segment de P3 à P4</Segment>
<Segment name="s3" n="14" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P2,P4)&gt;180)&amp;&amp;(a(P4,P2,P1)&gt;180))" from="P2" to="P4">Segment de P2 à P4</Segment>
<Segment name="s4" n="15" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P1,P4)&gt;180)&amp;&amp;(a(P4,P1,P7)&gt;180))" from="P1" to="P4">Segment de P1 à P4</Segment>
<Segment name="s5" n="16" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P1,P5)&gt;180)&amp;&amp;(a(P5,P1,P2)&gt;180))" from="P1" to="P5">Segment de P1 à P5</Segment>
<Segment name="s6" n="17" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P7,P5)&gt;180)&amp;&amp;(a(P5,P7,P1)&gt;180))" from="P7" to="P5">Segment de P7 à P5</Segment>
<Segment name="s7" n="18" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P3,P5)&gt;180)&amp;&amp;(a(P5,P3,P7)&gt;180))" from="P3" to="P5">Segment de P3 à P5</Segment>
<Segment name="s8" n="19" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P2,P5)&gt;180)&amp;&amp;(a(P5,P2,P3)&gt;180))" from="P2" to="P5">Segment de P2 à P5</Segment>
<Segment name="s9" n="20" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P2,P3)&gt;180)&amp;&amp;(a(P3,P2,P4)&gt;180))" from="P2" to="P3">Segment de P2 à P3</Segment>
<Segment name="s10" n="21" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P3,P7)&gt;180)&amp;&amp;(a(P7,P3,P4)&gt;180))" from="P3" to="P7">Segment de P3 à P7</Segment>
<Segment name="s11" n="22" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P1,P2)&gt;180)&amp;&amp;(a(P2,P1,P4)&gt;180))" from="P1" to="P2">Segment de P1 à P2</Segment>
<Segment name="s12" n="23" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P7,P1)&gt;180)&amp;&amp;(a(P1,P7,P4)&gt;180))" from="P7" to="P1">Segment de P7 à P1</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Disoc" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Expression name="E1" n="0" type="thick" hidden="super" showname="true" showvalue="true" x="6.954442800450053" y="1.2038141738163561" value="(1+sqrt(5))/2" prompt="Valeur">Expression &quot;(1+sqrt(5))/2&quot; à 6.95444, 1.20381</Expression>
<Point name="O" n="1" parameter="true" mainparameter="true" x="3.8348666310284085" y="0.14083182204016306">Point</Point>
<Point name="X" n="2" parameter="true" mainparameter="true" x="3.477722991984257" y="-0.07094796408050003">Point</Point>
<Point name="Y" n="3" parameter="true" mainparameter="true" x="4.7689161056764595" y="0.059855593752442815">Point</Point>
<Point name="Z" n="4" parameter="true" mainparameter="true" x="3.8348666310284085" y="1.1147887885653684">Point</Point>
<Point name="A" n="5" parameter="true" mainparameter="true" x="4.001636568107252" y="0.31769073946854853">Point</Point>
<Expression name="E2" n="6" type="thick" showname="true" showvalue="true" target="true" x="x(A)" y="y(A)+15/pixel" value="1" prompt="k" fixed="true">Expression &quot;1&quot; à 4.00164, 0.41236</Expression>
<Point name="P13" n="7" target="true" x="x(O)+(x3D(A)+ph*k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="4.357815495916042" y="y(O)+(x3D(A)+ph*k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="-0.1059523808925878" shape="dcross" is3D="true" x3D="x3D(A)+E1*E2" actx3D="1.6101665053726657" y3D="y3D(A)+E2" acty3D="1.1755368638231511" z3D="z3D(A)" actz3D="0.19447168935507608" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="8" target="true" x="x(O)+(x3D(A)-ph*k)*(x(X)-x(O))+(y3D(A)+k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="5.513556589594565" y="y(O)+(x3D(A)-ph*k)*(y(X)-y(O))+(y3D(A)+k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.5793814032542444" shape="dcross" is3D="true" x3D="x3D(A)-E1*E2" actx3D="-1.625901472127124" y3D="y3D(A)+E2" acty3D="1.1755368638231511" z3D="z3D(A)" actz3D="0.19447168935507608" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="9" target="true" x="x(O)+(x3D(A)-ph*k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="3.645457640298462" y="y(O)+(x3D(A)-ph*k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.7413338598296848" shape="dcross" is3D="true" x3D="x3D(A)-E1*E2" actx3D="-1.625901472127124" y3D="y3D(A)-E2" acty3D="-0.8244631361768489" z3D="z3D(A)" actz3D="0.19447168935507608" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="10" target="true" x="x(O)+(x3D(A)+ph*k)*(x(X)-x(O))+(y3D(A)-k)*(x(Y)-x(O))+(z3D(A))*(x(Z)-x(O))" actx="2.48971654661994" y="y(O)+(x3D(A)+ph*k)*(y(X)-y(O))+(y3D(A)-k)*(y(Y)-y(O))+(z3D(A))*(y(Z)-y(O))" acty="0.0560000756828527" shape="dcross" is3D="true" x3D="x3D(A)+E1*E2" actx3D="1.6101665053726657" y3D="y3D(A)-E2" acty3D="-0.8244631361768489" z3D="z3D(A)" actz3D="0.19447168935507608" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="11" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+ph*k)*(x(Z)-x(O))" actx="3.6444929290631007" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+ph*k)*(y(Z)-y(O))" acty="1.6818064287654115" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9921325166227708" y3D="y3D(A)" acty3D="0.17553686382315115" z3D="z3D(A)+E1*E2" actz3D="1.812505678104971" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="12" target="true" x="x(O)+(x3D(A)+k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-ph*k)*(x(Z)-x(O))" actx="3.6444929290631007" y="y(O)+(x3D(A)+k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-ph*k)*(y(Z)-y(O))" acty="-1.4699845220696404" shape="dcross" is3D="true" x3D="x3D(A)+E2" actx3D="0.9921325166227708" y3D="y3D(A)" acty3D="0.17553686382315115" z3D="z3D(A)-E1*E2" actz3D="-1.4235622993948187" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="13" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)-ph*k)*(x(Z)-x(O))" actx="4.358780207151404" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)-ph*k)*(y(Z)-y(O))" acty="-1.0464249498283142" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0078674833772292" y3D="y3D(A)" acty3D="0.17553686382315115" z3D="z3D(A)-E1*E2" actz3D="-1.4235622993948187" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="14" target="true" x="x(O)+(x3D(A)-k)*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+ph*k)*(x(Z)-x(O))" actx="4.358780207151404" y="y(O)+(x3D(A)-k)*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+ph*k)*(y(Z)-y(O))" acty="2.105366001006738" shape="dcross" is3D="true" x3D="x3D(A)-E2" actx3D="-1.0078674833772292" y3D="y3D(A)" acty3D="0.17553686382315115" z3D="z3D(A)+E1*E2" actz3D="1.812505678104971" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="15" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)+ph*k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="5.512960365261782" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)+ph*k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.160625416343452" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.007867483377229156" y3D="y3D(A)+E1*E2" acty3D="1.793570852573046" z3D="z3D(A)+E2" actz3D="1.1944716893550762" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="16" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)-ph*k)*(x(Y)-x(O))+(z3D(A)+k)*(x(Z)-x(O))" actx="2.4903127709527224" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)-ph*k)*(y(Y)-y(O))+(z3D(A)+k)*(y(Z)-y(O))" acty="1.422669995644056" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.007867483377229156" y3D="y3D(A)-E1*E2" acty3D="-1.4424971249267438" z3D="z3D(A)+E2" actz3D="1.1944716893550762" fixed="true" fixed3D="true">Point</Point>
<Point name="P10" n="17" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)-ph*k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="2.4903127709527224" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)-ph*k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.5252439374063547" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.007867483377229156" y3D="y3D(A)-E1*E2" acty3D="-1.4424971249267438" z3D="z3D(A)-E2" actz3D="-0.8055283106449239" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="18" target="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A)+ph*k)*(x(Y)-x(O))+(z3D(A)-k)*(x(Z)-x(O))" actx="5.512960365261782" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A)+ph*k)*(y(Y)-y(O))+(z3D(A)-k)*(y(Z)-y(O))" acty="-0.7872885167069588" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="-0.007867483377229156" y3D="y3D(A)+E1*E2" acty3D="1.793570852573046" z3D="z3D(A)-E2" actz3D="-0.8055283106449239" fixed="true" fixed3D="true">Point</Point>
<Segment name="s1" n="19" color="4" target="true" ctag0="thin" cexpr0="((a(P8,P13,P4)&gt;180)&amp;&amp;(a(P4,P13,P3)&gt;180))" from="P13" to="P4" is3D="true">Segment de P13 à P4</Segment>
<Segment name="s2" n="20" color="4" target="true" ctag0="thin" cexpr0="((a(P13,P3,P4)&gt;180)&amp;&amp;(a(P4,P3,P9)&gt;180))" from="P3" to="P4" is3D="true">Segment de P3 à P4</Segment>
<Segment name="s3" n="21" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P13,P11)&gt;180)&amp;&amp;(a(P11,P13,P8)&gt;180))" from="P13" to="P11" is3D="true">Segment de P13 à P11</Segment>
<Segment name="s4" n="22" color="4" target="true" ctag0="thin" cexpr0="((a(P3,P13,P5)&gt;180)&amp;&amp;(a(P5,P13,P11)&gt;180))" from="P13" to="P5" is3D="true">Segment de P13 à P5</Segment>
<Segment name="s5" n="23" color="4" target="true" ctag0="thin" cexpr0="((a(P4,P13,P3)&gt;180)&amp;&amp;(a(P3,P13,P5)&gt;180))" from="P13" to="P3" is3D="true">Segment de P13 à P3</Segment>
<Segment name="s6" n="24" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P8,P4)&gt;180)&amp;&amp;(a(P4,P8,P13)&gt;180))" from="P8" to="P4" is3D="true">Segment de P8 à P4</Segment>
<Segment name="s7" n="25" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P8,P7)&gt;180)&amp;&amp;(a(P7,P8,P4)&gt;180))" from="P8" to="P7" is3D="true">Segment de P8 à P7</Segment>
<Segment name="s8" n="26" color="4" target="true" ctag0="thin" cexpr0="((a(P13,P8,P11)&gt;180)&amp;&amp;(a(P11,P8,P1)&gt;180))" from="P8" to="P11" is3D="true">Segment de P8 à P11</Segment>
<Segment name="s9" n="27" color="4" target="true" ctag0="thin" cexpr0="((a(P8,P4,P7)&gt;180)&amp;&amp;(a(P7,P4,P9)&gt;180))" from="P4" to="P7" is3D="true">Segment de P4 à P7</Segment>
<Segment name="s10" n="28" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P4,P9)&gt;180)&amp;&amp;(a(P9,P4,P3)&gt;180))" from="P4" to="P9" is3D="true">Segment de P4 à P9</Segment>
<Segment name="s11" n="29" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P9,P3)&gt;180)&amp;&amp;(a(P3,P9,P4)&gt;180))" from="P9" to="P3">Segment de P9 à P3</Segment>
<Segment name="s12" n="30" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P7,P9)&gt;180)&amp;&amp;(a(P9,P7,P4)&gt;180))" from="P7" to="P9">Segment de P7 à P9</Segment>
<Segment name="s13" n="31" color="4" target="true" ctag0="thin" cexpr0="((a(P7,P1,P8)&gt;180)&amp;&amp;(a(P8,P1,P11)&gt;180))" from="P1" to="P8">Segment de P1 à P8</Segment>
<Segment name="s14" n="32" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P1,P7)&gt;180)&amp;&amp;(a(P7,P1,P8)&gt;180))" from="P1" to="P7">Segment de P1 à P7</Segment>
<Segment name="s15" n="33" color="4" target="true" ctag0="thin" cexpr0="((a(P9,P2,P7)&gt;180)&amp;&amp;(a(P7,P2,P1)&gt;180))" from="P2" to="P7">Segment de P2 à P7</Segment>
<Segment name="s16" n="34" color="4" target="true" ctag0="thin" cexpr0="((a(P11,P13,P8)&gt;180)&amp;&amp;(a(P8,P13,P4)&gt;180))" from="P13" to="P8">Segment de P13 à P8</Segment>
<Segment name="s17" n="35" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P1,P2)&gt;180)&amp;&amp;(a(P2,P1,P7)&gt;180))" from="P1" to="P2">Segment de P1 à P2</Segment>
<Segment name="s18" n="36" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P2,P9)&gt;180)&amp;&amp;(a(P9,P2,P7)&gt;180))" from="P2" to="P9">Segment de P2 à P9</Segment>
<Segment name="s19" n="37" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P9,P10)&gt;180)&amp;&amp;(a(P10,P9,P3)&gt;180))" from="P9" to="P10">Segment de P9 à P10</Segment>
<Segment name="s20" n="38" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P10,P3)&gt;180)&amp;&amp;(a(P3,P10,P9)&gt;180))" from="P10" to="P3">Segment de P10 à P3</Segment>
<Segment name="s21" n="39" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P3,P5)&gt;180)&amp;&amp;(a(P5,P3,P13)&gt;180))" from="P3" to="P5">Segment de P3 à P5</Segment>
<Segment name="s22" n="40" color="4" target="true" ctag0="thin" cexpr0="((a(P13,P11,P5)&gt;180)&amp;&amp;(a(P5,P11,P6)&gt;180))" from="P11" to="P5">Segment de P11 à P5</Segment>
<Segment name="s23" n="41" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P11,P1)&gt;180)&amp;&amp;(a(P1,P11,P8)&gt;180))" from="P11" to="P1">Segment de P11 à P1</Segment>
<Segment name="s24" n="42" color="4" target="true" ctag0="thin" cexpr0="((a(P10,P5,P6)&gt;180)&amp;&amp;(a(P6,P5,P11)&gt;180))" from="P5" to="P6">Segment de P5 à P6</Segment>
<Segment name="s25" n="43" color="4" target="true" ctag0="thin" cexpr0="((a(P5,P11,P6)&gt;180)&amp;&amp;(a(P6,P11,P1)&gt;180))" from="P11" to="P6">Segment de P11 à P6</Segment>
<Segment name="s26" n="44" color="4" target="true" ctag0="thin" cexpr0="((a(P11,P1,P6)&gt;180)&amp;&amp;(a(P6,P1,P2)&gt;180))" from="P1" to="P6">Segment de P1 à P6</Segment>
<Segment name="s27" n="45" color="4" target="true" ctag0="thin" cexpr0="((a(P6,P10,P5)&gt;180)&amp;&amp;(a(P5,P10,P3)&gt;180))" from="P10" to="P5">Segment de P10 à P5</Segment>
<Segment name="s28" n="46" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P2,P10)&gt;180)&amp;&amp;(a(P10,P2,P9)&gt;180))" from="P2" to="P10">Segment de P2 à P10</Segment>
<Segment name="s29" n="47" color="4" target="true" ctag0="thin" cexpr0="((a(P2,P10,P6)&gt;180)&amp;&amp;(a(P6,P10,P5)&gt;180))" from="P10" to="P6">Segment de P10 à P6</Segment>
<Segment name="s30" n="48" color="4" target="true" ctag0="thin" cexpr0="((a(P1,P2,P6)&gt;180)&amp;&amp;(a(P6,P2,P10)&gt;180))" from="P2" to="P6">Segment de P2 à P6</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcoords" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="p">p</Parameter>
<Objects>
<Point name="O" n="0" mainparameter="true" x="1.039330361771076" y="0.4647413122295063">Point à 1.03933, 0.46474</Point>
<Point name="p" n="1" color="1" mainparameter="true" x="x(p)" actx="-2.5312001282524608" y="y(p)" fixed="true">Point à &quot;x(p)&quot;, &quot;y(p)&quot;</Point>
<Point name="Z" n="2" mainparameter="true" x="1.015777063361877" y="1.4596355468668833">Point à 1.01578, 1.45964</Point>
<Point name="Y" n="3" mainparameter="true" x="2.0062468607753905" y="0.4624709308305989">Point à 2.00625, 0.46247</Point>
<Point name="X" n="4" mainparameter="true" x="0.7853269639841833" y="0.363843803570412">Point à 0.78533, 0.36384</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/symc" showduplicates="true">
<Parameter name="O">O</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="M" n="0" parameter="true" mainparameter="true" x="-2.569343065693431" y="4.131386861313868">Point à -2.57, 4.13</Point>
<Point name="O" n="1" parameter="true" mainparameter="true" x="-0.6715328467153284" y="2.700729927007301">Point à -0.67, 2.7</Point>
<Point name="P3" n="2" showname="true" target="true" x="2*x(O)-x(M)" actx="1.2262773722627742" y="2*y(O)-y(M)" acty="1.270072992700734" shape="circle" fixed="true">Point à &quot;2*x(O)-x(M)&quot;, &quot;2*y(O)-y(M)&quot;</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dsymc" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.3682663084045625" y="0.26687213529862674">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.15754911171457447" y="-0.08955486944906033">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.218865005773031" y="0.046539308757716835">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.3682663084045625" y="1.1748441810330126">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="-1.461754667871635" y="0.8939347976724219">Point</Point>
<Point name="B" n="5" parameter="true" mainparameter="true" x="-0.16478170213042512" y="1.5979519900145918">Point</Point>
<Point name="D" n="6" showname="true" target="true" x="x(O)+(2*x3D(B)-x3D(A))*(x(X)-x(O))+(2*y3D(B)-y3D(A))*(x(Y)-x(O))+(2*z3D(B)-z3D(A))*(x(Z)-x(O))" actx="1.132191263610785" y="y(O)+(2*x3D(B)-x3D(A))*(y(X)-y(O))+(2*y3D(B)-y3D(A))*(y(Y)-y(O))+(2*z3D(B)-z3D(A))*(y(Z)-y(O))" acty="2.3019691823567614" shape="dcross" is3D="true" x3D="2*x3D(B)-x3D(A)" actx3D="1.2248710589594154" y3D="2*y3D(B)-y3D(A)" acty3D="1.6552823911212977" z3D="2*z3D(B)-z3D(A)" actz3D="3.123870642372246" fixed="true" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/syma" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="A" n="0" x="1.2070175438596493" y="2.091228070175438">Point à 1.20702, 2.09123</Point>
<Point name="P6" n="1" color="2" hidden="super" x="x(A)" actx="1.2070175438596493" y="y(A)" acty="2.091228070175438" shape="circle" fixed="true">Point à &quot;x(A)&quot;, &quot;y(A)&quot;</Point>
<Line name="l1" n="2">???</Line>
<PointOn name="P1" n="3" color="2" hidden="super" on="l1" alpha="9.884663042646986" x="2.34672934754846" y="-0.6763300486598602" shape="circle">Point sur l1</PointOn>
<Plumb name="perp1" n="4" color="3" hidden="super" point="P6" line="l1" valid="true">Perpendiculaire passant par P6 à l1</Plumb>
<Circle name="c1" n="5" color="4" hidden="super" through="P6" midpoint="P1" acute="true">Cercle de centre P1 passant par P6</Circle>
<Intersection name="P2" n="6" color="2" target="true" first="perp1" second="c1" awayfrom="P6" shape="circle" which="second">Intersection entre perp1 et c1</Intersection>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dsymp" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Parameter name="P3">P3</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="O" n="0" x="-0.08766889016744833" y="-0.30433143442621824">Point</Point>
<Point name="X" n="1" x="0.19722502103317824" y="-0.7853726378776342">Point</Point>
<Point name="Y" n="2" x="0.870890163496336" y="-0.16136088986131425">Point</Point>
<Point name="Z" n="3" x="-0.08766889016744833" y="0.5606302895644033">Point</Point>
<Point name="P1" n="4" x="-0.3339931658296662" y="-0.1826098678936916">Point</Point>
<Point name="P2" n="5" x="0.780381867926975" y="0.007245065333348233">Point</Point>
<Point name="P3" n="6" x="-2.2896809086956433" y="1.6206921278766329">Point</Point>
<Point name="A" n="7" hidden="super" showname="true" x="x(O)+((y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1)))*(x(X)-x(O))+((z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1)))*(x(Y)-x(O))+((x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1)))*(x(Z)-x(O))" actx="9.7163249090668" y="y(O)+((y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1)))*(y(X)-y(O))+((z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1)))*(y(Y)-y(O))+((x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1)))*(y(Z)-y(O))" acty="13.632930991067662" shape="dcross" is3D="true" x3D="(y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1))" actx3D="-1.9372949999999998" y3D="(z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1))" acty3D="10.803629999999998" z3D="(x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1))" actz3D="13.25" fixed="true" fixed3D="true">Point</Point>
<Point name="M" n="8" x="-0.46287097534024657" y="1.8142361658484853">Point</Point>
<Expression name="E1" n="9" type="thick" hidden="super" showname="true" showvalue="true" x="1.700139742385291" y="0.9240661098761316" value="(x3D(A)*(x3D(M)-x3D(P1))+y3D(A)*(y3D(M)-y3D(P1))+z3D(A)*(z3D(M)-z3D(P1)))/(x3D(A)*x3D(A)+y3D(A)*y3D(A)+z3D(A)*z3D(A))" prompt="Valeur">Expression &quot;(x3D(A)*(x3D(M)-x3D(P1))+y3D(A)*(y3D(M)-y3D(P1))+z3D(A)*(z3D(M)-z3D(P1)))/(x3D(A)*x3D(A)+y3D(A)*y3D(A)+z3D(A)*z3D(A))&quot; à 1.70014, 0.92407</Expression>
<Point name="P4" n="10" showname="true" target="true" x="x(O)+(x3D(M)-2*E1*x3D(A))*(x(X)-x(O))+(y3D(M)-2*E1*y3D(A))*(x(Y)-x(O))+(z3D(M)-2*E1*z3D(A))*(x(Z)-x(O))" actx="-1.9317949441559825" y="y(O)+(x3D(M)-2*E1*x3D(A))*(y(X)-y(O))+(y3D(M)-2*E1*y3D(A))*(y(Y)-y(O))+(z3D(M)-2*E1*z3D(A))*(y(Z)-y(O))" acty="-0.27397189055419646" shape="dcross" is3D="true" x3D="x3D(M)-2*E1*x3D(A)" actx3D="0.2642651410567184" y3D="y3D(M)-2*E1*y3D(A)" acty3D="-2.0023947155700275" z3D="z3D(M)-2*E1*z3D(A)" actz3D="0.5130463186599321" fixed="true" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dproj" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="P1">P1</Parameter>
<Parameter name="P2">P2</Parameter>
<Parameter name="P3">P3</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="O" n="0" x="-0.08766889016744833" y="-0.30433143442621824">Point</Point>
<Point name="X" n="1" x="0.19722502103317824" y="-0.7853726378776342">Point</Point>
<Point name="Y" n="2" x="0.870890163496336" y="-0.16136088986131425">Point</Point>
<Point name="Z" n="3" x="-0.08766889016744833" y="0.5606302895644033">Point</Point>
<Point name="P1" n="4" x="-0.3339931658296662" y="-0.1826098678936916">Point</Point>
<Point name="P2" n="5" x="0.780381867926975" y="0.007245065333348233">Point</Point>
<Point name="P3" n="6" x="-2.2896809086956433" y="1.6206921278766329">Point</Point>
<Point name="A" n="7" hidden="super" showname="true" x="x(O)+((y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1)))*(x(X)-x(O))+((z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1)))*(x(Y)-x(O))+((x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1)))*(x(Z)-x(O))" actx="9.7163249090668" y="y(O)+((y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1)))*(y(X)-y(O))+((z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1)))*(y(Y)-y(O))+((x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1)))*(y(Z)-y(O))" acty="13.632930991067662" shape="dcross" is3D="true" x3D="(y3D(P2)-y3D(P1))*(z3D(P3)-z3D(P1))-(z3D(P2)-z3D(P1))*(y3D(P3)-y3D(P1))" actx3D="-1.9372949999999998" y3D="(z3D(P2)-z3D(P1))*(x3D(P3)-x3D(P1))-(x3D(P2)-x3D(P1))*(z3D(P3)-z3D(P1))" acty3D="10.803629999999998" z3D="(x3D(P2)-x3D(P1))*(y3D(P3)-y3D(P1))-(y3D(P2)-y3D(P1))*(x3D(P3)-x3D(P1))" actz3D="13.25" fixed="true" fixed3D="true">Point</Point>
<Point name="M" n="8" x="-0.46287097534024657" y="1.8142361658484853">Point</Point>
<Expression name="E1" n="9" type="thick" hidden="super" showname="true" showvalue="true" x="1.700139742385291" y="0.9240661098761316" value="(x3D(A)*(x3D(M)-x3D(P1))+y3D(A)*(y3D(M)-y3D(P1))+z3D(A)*(z3D(M)-z3D(P1)))/(x3D(A)*x3D(A)+y3D(A)*y3D(A)+z3D(A)*z3D(A))" prompt="Valeur">Expression &quot;(x3D(A)*(x3D(M)-x3D(P1))+y3D(A)*(y3D(M)-y3D(P1))+z3D(A)*(z3D(M)-z3D(P1)))/(x3D(A)*x3D(A)+y3D(A)*y3D(A)+z3D(A)*z3D(A))&quot; à 1.70014, 0.92407</Expression>
<Point name="P4" n="10" showname="true" target="true" x="x(O)+(x3D(M)-E1*x3D(A))*(x(X)-x(O))+(y3D(M)-E1*y3D(A))*(x(Y)-x(O))+(z3D(M)-E1*z3D(A))*(x(Z)-x(O))" actx="-1.1973329597481144" y="y(O)+(x3D(M)-E1*x3D(A))*(y(X)-y(O))+(y3D(M)-E1*y3D(A))*(y(Y)-y(O))+(z3D(M)-E1*z3D(A))*(y(Z)-y(O))" acty="0.7701321376471445" shape="dcross" is3D="true" x3D="x3D(M)-E1*x3D(A)" actx3D="0.11913351824861595" y3D="y3D(M)-E1*y3D(A)" acty3D="-1.1930454145506681" z3D="z3D(M)-E1*z3D(A)" actz3D="1.5056643774518856" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/trans" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="A" n="0" parameter="true" mainparameter="true" x="-3.9850187265917603" y="1.730337078651686">Point à -3.98502, 1.73034 </Point>
<Point name="B" n="1" parameter="true" mainparameter="true" x="-2.411985018726593" y="2.269662921348316">Point à -2.41199, 2.26966 </Point>
<Point name="M" n="2" parameter="true" mainparameter="true" x="-1.8726591760299642" y="1.1610486891385765">Point à -1.87266, 1.16105 </Point>
<Point name="P4" n="3" target="true" bold="true" x="x(M)+x(B)-x(A)" actx="-0.2996254681647974" y="y(M)+y(B)-y(A)" acty="1.7003745318352066" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dtrans" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.4442578073977588" y="0.370811446580833">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.19691192668644963" y="-0.07346785970021119">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.2116569031063644" y="-3.884235523203272E-4">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.4442578073977588" y="1.1861804148544386">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="-1.831046008349229" y="1.7445480000975029">Point</Point>
<Point name="B" n="5" parameter="true" mainparameter="true" x="-0.15280994476887355" y="2.536634112669619">Point</Point>
<Point name="C" n="6" parameter="true" mainparameter="true" x="-0.7930362228744573" y="1.2192912791318204">Point</Point>
<Point name="E" n="7" showname="true" target="true" x="x(O)+(x3D(C)+x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(C)+y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(C)+z3D(B)-z3D(A))*(x(Z)-x(O))" actx="0.8851998407058981" y="y(O)+(x3D(C)+x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(C)+y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(C)+z3D(B)-z3D(A))*(y(Z)-y(O))" acty="2.011377391703936" shape="dcross" is3D="true" x3D="x3D(C)+x3D(B)-x3D(A)" actx3D="-1.2958802487984546" y3D="y3D(C)+y3D(B)-y3D(A)" acty3D="-0.5081282521695978" z3D="z3D(C)+z3D(B)-z3D(A)" actz3D="1.0746251820234274" fixed="true" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcircle1" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="l1">l1</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="O" n="0" x="0.7159232251922991" y="-0.6668339311102434">Point</Point>
<Point name="X" n="1" x="-0.24152408209807008" y="-0.8523787087830506">Point</Point>
<Point name="Y" n="2" x="1.0045313570292613" y="-1.2823721927451424">Point</Point>
<Point name="Z" n="3" x="0.7159232251922991" y="0.09912023458234887">Point</Point>
<Point name="P" n="4" x="-2.571700097015428" y="0.6632542726375268">Point</Point>
<Point name="P1" n="5" x="0.3257318982105539" y="1.3331412222150743">Point</Point>
<Point name="C" n="6" x="4.8271480703400425" y="0.11406125475622053">Point</Point>
<Line name="l1" n="7" color="5" type="thin" mainparameter="true" from="P" to="P1">Droite passant par P et P1</Line>
<Point name="P2" n="8" hidden="super" showname="true" x="x(O)+(x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(B)-z3D(A))*(x(Z)-x(O))" actx="3.6133552204182813" y="y(O)+(x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(B)-z3D(A))*(y(Z)-y(O))" acty="0.0030530184673038807" shape="dcross" is3D="true" x3D="x3D(P1)-x3D(P)" actx3D="-3.660710860675136" y3D="y3D(P1)-y3D(P)" acty3D="-2.1049502563539106" z3D="z3D(P1)-z3D(P)" actz3D="-1.7037785200043254" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="9" hidden="super" showname="true" x="x(O)+(x3D(D)/d3D(O,D))*(x(X)-x(O))+(y3D(D)/d3D(O,D))*(x(Y)-x(O))+(z3D(D)/d3D(O,D))*(x(Z)-x(O))" actx="1.3522301780578065" y="y(O)+(x3D(D)/d3D(O,D))*(y(X)-y(O))+(y3D(D)/d3D(O,D))*(y(Y)-y(O))+(z3D(D)/d3D(O,D))*(y(Z)-y(O))" acty="-0.519719615967598" shape="dcross" is3D="true" x3D="x3D(P2)/d3D(O,P2)" actx3D="-0.8039311282942435" y3D="y3D(P2)/d3D(O,P2)" acty3D="-0.46226951512957287" z3D="z3D(P2)/d3D(O,P2)" actz3D="-0.3741679253241976" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="10" hidden="super" showname="true" x="x(O)+(if(x3D(E)==0,0,-y3D(E)/x3D(E)))*(x(X)-x(O))+(if(x3D(E)==0,if(y3D(E)==0,1,-z3D(E)/y3D(E)),1))*(x(Y)-x(O))+(if(x3D(E)==0,if(y3D(E)==0,-y3D(E)/z3D(E),1),0))*(x(Z)-x(O))" actx="1.5550744159133307" y="y(O)+(if(x3D(E)==0,0,-y3D(E)/x3D(E)))*(y(X)-y(O))+(if(x3D(E)==0,if(y3D(E)==0,1,-z3D(E)/y3D(E)),1))*(y(Y)-y(O))+(if(x3D(E)==0,if(y3D(E)==0,-y3D(E)/z3D(E),1),0))*(y(Z)-y(O))" acty="-1.1756818415559542" shape="dcross" is3D="true" x3D="if(x3D(P3)==0,0,-y3D(P3)/x3D(P3))" actx3D="-0.5750113397280713" y3D="if(x3D(P3)==0,if(y3D(P3)==0,1,-z3D(P3)/y3D(P3)),1)" acty3D="1.0" z3D="if(x3D(P3)==0,if(y3D(P3)==0,-y3D(P3)/z3D(P3),1),0)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="11" hidden="super" showname="true" x="x(O)+(x3D(F)/d3D(O,F))*(x(X)-x(O))+(y3D(F)/d3D(O,F))*(x(Y)-x(O))+(z3D(F)/d3D(O,F))*(x(Z)-x(O))" actx="1.44338511690321" y="y(O)+(x3D(F)/d3D(O,F))*(y(X)-y(O))+(y3D(F)/d3D(O,F))*(y(Y)-y(O))+(z3D(F)/d3D(O,F))*(y(Z)-y(O))" acty="-1.1079552303696993" shape="dcross" is3D="true" x3D="x3D(P4)/d3D(O,P4)" actx3D="-0.49847851207169136" y3D="y3D(P4)/d3D(O,P4)" acty3D="0.8669020550228226" z3D="z3D(P4)/d3D(O,P4)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="12" hidden="super" showname="true" x="x(O)+(y3D(E)*z3D(G)-z3D(E)*y3D(G))*(x(X)-x(O))+(z3D(E)*x3D(G)-x3D(E)*z3D(G))*(x(Y)-x(O))+(x3D(E)*y3D(G)-y3D(E)*x3D(G))*(x(Z)-x(O))" actx="0.45918861933754473" y="y(O)+(y3D(E)*z3D(G)-z3D(E)*y3D(G))*(y(X)-y(O))+(z3D(E)*x3D(G)-x3D(E)*z3D(G))*(y(Y)-y(O))+(x3D(E)*y3D(G)-y3D(E)*x3D(G))*(y(Z)-y(O))" acty="-1.5521414356643164" shape="dcross" is3D="true" x3D="y3D(P3)*z3D(P5)-z3D(P3)*y3D(P5)" actx3D="0.3243669433871729" y3D="z3D(P3)*x3D(P5)-x3D(P3)*z3D(P5)" acty3D="0.18651467068055777" z3D="x3D(P3)*y3D(P5)-y3D(P3)*x3D(P5)" actz3D="-0.9273609672929879" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="13" hidden="super" showname="true" x="x(O)+(x3D(C)+x3D(G))*(x(X)-x(O))+(y3D(C)+y3D(G))*(x(Y)-x(O))+(z3D(C)+z3D(G))*(x(Z)-x(O))" actx="5.554609962050953" y="y(O)+(x3D(C)+x3D(G))*(y(X)-y(O))+(y3D(C)+y3D(G))*(y(Y)-y(O))+(z3D(C)+z3D(G))*(y(Z)-y(O))" acty="-0.32706004450323545" shape="dcross" is3D="true" x3D="x3D(C)+x3D(P5)" actx3D="-5.404674812085888" y3D="y3D(C)+y3D(P5)" acty3D="-1.1642243294200554" z3D="z3D(C)+z3D(P5)" actz3D="-1.8012303888079884" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="14" hidden="super" showname="true" x="x(O)+(x3D(C)+x3D(H))*(x(X)-x(O))+(y3D(C)+y3D(H))*(x(Y)-x(O))+(z3D(C)+z3D(H))*(x(Z)-x(O))" actx="4.570413464485288" y="y(O)+(x3D(C)+x3D(H))*(y(X)-y(O))+(y3D(C)+y3D(H))*(y(Y)-y(O))+(z3D(C)+z3D(H))*(y(Z)-y(O))" acty="-0.7712462497978527" shape="dcross" is3D="true" x3D="x3D(C)+x3D(P6)" actx3D="-4.581829356627024" y3D="y3D(C)+y3D(P6)" acty3D="-1.8446117137623201" z3D="z3D(C)+z3D(P6)" actz3D="-2.728591356100976" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="15" hidden="super" showname="true" x="x(O)+((y3D(I)-y3D(C))*(z3D(J)-z3D(C))-(z3D(I)-z3D(C))*(y3D(J)-y3D(C)))*(x(X)-x(O))+((z3D(I)-z3D(C))*(x3D(J)-x3D(C))-(x3D(I)-x3D(C))*(z3D(J)-z3D(C)))*(x(Y)-x(O))+((x3D(I)-x3D(C))*(y3D(J)-y3D(C))-(y3D(I)-y3D(C))*(x3D(J)-x3D(C)))*(x(Z)-x(O))" actx="1.3522301780578065" y="y(O)+((y3D(I)-y3D(C))*(z3D(J)-z3D(C))-(z3D(I)-z3D(C))*(y3D(J)-y3D(C)))*(y(X)-y(O))+((z3D(I)-z3D(C))*(x3D(J)-x3D(C))-(x3D(I)-x3D(C))*(z3D(J)-z3D(C)))*(y(Y)-y(O))+((x3D(I)-x3D(C))*(y3D(J)-y3D(C))-(y3D(I)-y3D(C))*(x3D(J)-x3D(C)))*(y(Z)-y(O))" acty="-0.5197196159675983" shape="dcross" is3D="true" x3D="(y3D(P7)-y3D(C))*(z3D(P8)-z3D(C))-(z3D(P7)-z3D(C))*(y3D(P8)-y3D(C))" actx3D="-0.8039311282942436" y3D="(z3D(P7)-z3D(C))*(x3D(P8)-x3D(C))-(x3D(P7)-x3D(C))*(z3D(P8)-z3D(C))" acty3D="-0.4622695151295728" z3D="(x3D(P7)-x3D(C))*(y3D(P8)-y3D(C))-(y3D(P7)-y3D(C))*(x3D(P8)-x3D(C))" actz3D="-0.37416792532419796" fixed="true" fixed3D="true">Point</Point>
<Expression name="E1" n="16" type="thick" hidden="super" showname="true" showvalue="true" x="2.1428133400514886" y="1.05351476141561" value="(x3D(P9)*(x3D(P)-x3D(C))+y3D(P9)*(y3D(P)-y3D(C))+z3D(P9)*(z3D(P)-z3D(C)))/(x3D(P9)*x3D(P9)+y3D(P9)*y3D(P9)+z3D(P9)*z3D(P9))" prompt="Valeur">Expression &quot;(x3D(P9)*(x3D(P)-x3D(C))+y3D(P9)*(y3D(P)-y3D(C))+z3D(P9)*(z3D(P)-z3D(C)))/(x3D(P9)*x3D(P9)+y3D(P9)*y3D(P9)+z3D(P9)*z3D(P9))&quot; à 2.14281, 1.05351</Expression>
<Point name="P10" n="17" showname="false" x="x(O)+(x3D(A)-E1*x3D(P5))*(x(X)-x(O))+(y3D(A)-E1*y3D(P5))*(x(Y)-x(O))+(z3D(A)-E1*z3D(P5))*(x(Z)-x(O))" actx="2.9461248815992986" y="y(O)+(x3D(A)-E1*x3D(P5))*(y(X)-y(O))+(y3D(A)-E1*y3D(P5))*(y(Y)-y(O))+(z3D(A)-E1*z3D(P5))*(y(Z)-y(O))" acty="1.9389766879754293" shape="dcross" is3D="true" x3D="x3D(P)-E1*x3D(P9)" actx3D="-3.7148434544269016" y3D="y3D(P)-E1*y3D(P9)" acty3D="-4.59642352277406" z3D="z3D(P)-E1*z3D(P9)" actz3D="-1.1916296948082263" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="18" hidden="super" showname="true" x="x(O)+(x3D(C)-x3D(K))*(x(X)-x(O))+(y3D(C)-y3D(K))*(x(Y)-x(O))+(z3D(C)-z3D(K))*(x(Z)-x(O))" actx="2.596946413933042" y="y(O)+(x3D(C)-x3D(K))*(y(X)-y(O))+(y3D(C)-y3D(K))*(y(Y)-y(O))+(z3D(C)-z3D(K))*(y(Z)-y(O))" acty="-2.491749364329452" shape="dcross" is3D="true" x3D="x3D(C)-x3D(P10)" actx3D="-1.1913528455872955" y3D="y3D(C)-y3D(P10)" acty3D="2.5652971383311822" z3D="z3D(C)-z3D(P10)" actz3D="-0.609600693999762" fixed="true" fixed3D="true">Point</Point>
<Point name="P12" n="19" hidden="super" showname="true" x="x(O)+(y3D(L)*z3D(E)-z3D(L)*y3D(E))*(x(X)-x(O))+(z3D(L)*x3D(E)-x3D(L)*z3D(E))*(x(Y)-x(O))+(x3D(L)*y3D(E)-y3D(L)*x3D(E))*(x(Z)-x(O))" actx="1.9175278270376221" y="y(O)+(y3D(L)*z3D(E)-z3D(L)*y3D(E))*(y(X)-y(O))+(z3D(L)*x3D(E)-x3D(L)*z3D(E))*(y(Y)-y(O))+(x3D(L)*y3D(E)-y3D(L)*x3D(E))*(y(Z)-y(O))" acty="1.5377482261271773" shape="dcross" is3D="true" x3D="y3D(P11)*z3D(P3)-z3D(P11)*y3D(P3)" actx3D="-1.2416517253274009" y3D="z3D(P11)*x3D(P3)-x3D(P11)*z3D(P3)" acty3D="0.04431095117370504" z3D="x3D(P11)*y3D(P3)-y3D(P11)*x3D(P3)" actz3D="2.6130483251064573" fixed="true" fixed3D="true">Point</Point>
<Point name="P13" n="20" hidden="super" showname="true" x="x(O)+(x3D(K)+cos(72)*x3D(L)+sin(72)*x3D(M))*(x(X)-x(O))+(y3D(K)+cos(72)*y3D(L)+sin(72)*y3D(M))*(x(Y)-x(O))+(z3D(K)+cos(72)*z3D(L)+sin(72)*z3D(M))*(x(Z)-x(O))" actx="4.6701869003287815" y="y(O)+(x3D(K)+cos(72)*x3D(L)+sin(72)*x3D(M))*(y(X)-y(O))+(y3D(K)+cos(72)*y3D(L)+sin(72)*y3D(M))*(y(Y)-y(O))+(z3D(K)+cos(72)*z3D(L)+sin(72)*z3D(M))*(y(Z)-y(O))" acty="3.4717290321622496" shape="circle" is3D="true" x3D="x3D(P10)+cos(72)*x3D(P11)+sin(72)*x3D(P12)" actx3D="-5.263872694352073" y3D="y3D(P10)+cos(72)*y3D(P11)+sin(72)*y3D(P12)" acty3D="-3.7615608925513158" z3D="z3D(P10)+cos(72)*z3D(P11)+sin(72)*z3D(P12)" actz3D="1.1051499679497183" fixed="true" fixed3D="true">Point</Point>
<Point name="P14" n="21" hidden="super" showname="true" x="x(O)+(x3D(K)+cos(144)*x3D(L)+sin(144)*x3D(M))*(x(X)-x(O))+(y3D(K)+cos(144)*y3D(L)+sin(144)*y3D(M))*(x(Y)-x(O))+(z3D(K)+cos(144)*z3D(L)+sin(144)*z3D(M))*(x(Z)-x(O))" actx="2.1306306191461344" y="y(O)+(x3D(K)+cos(144)*x3D(L)+sin(144)*x3D(M))*(y(X)-y(O))+(y3D(K)+cos(144)*y3D(L)+sin(144)*y3D(M))*(y(Y)-y(O))+(z3D(K)+cos(144)*z3D(L)+sin(144)*z3D(M))*(y(Z)-y(O))" acty="4.711185166238171" shape="circle" is3D="true" x3D="x3D(P10)+cos(144)*x3D(P11)+sin(144)*x3D(P12)" actx3D="-3.480843328680778" y3D="y3D(P10)+cos(144)*y3D(P11)+sin(144)*y3D(P12)" acty3D="-6.64574717969045" z3D="z3D(P10)+cos(144)*z3D(P11)+sin(144)*z3D(P12)" actz3D="0.8374588954454669" fixed="true" fixed3D="true">Point</Point>
<Point name="P15" n="22" hidden="super" showname="true" x="x(O)+(x3D(K)+cos(216)*x3D(L)+sin(216)*x3D(M))*(x(X)-x(O))+(y3D(K)+cos(216)*y3D(L)+sin(216)*y3D(M))*(x(Y)-x(O))+(z3D(K)+cos(216)*z3D(L)+sin(216)*z3D(M))*(x(Z)-x(O))" actx="0.7180596910432335" y="y(O)+(x3D(K)+cos(216)*x3D(L)+sin(216)*x3D(M))*(y(X)-y(O))+(y3D(K)+cos(216)*y3D(L)+sin(216)*y3D(M))*(y(Y)-y(O))+(z3D(K)+cos(216)*z3D(L)+sin(216)*z3D(M))*(y(Z)-y(O))" acty="2.119543407255607" shape="circle" is3D="true" x3D="x3D(P10)+cos(216)*x3D(P11)+sin(216)*x3D(P12)" actx3D="-2.021194183418876" y3D="y3D(P10)+cos(216)*y3D(P11)+sin(216)*y3D(P12)" acty3D="-6.697837826920363" z3D="z3D(P10)+cos(216)*z3D(P11)+sin(216)*z3D(P12)" actz3D="-2.23436364260478" fixed="true" fixed3D="true">Point</Point>
<Point name="P16" n="23" hidden="super" showname="true" x="x(O)+(x3D(K)+cos(288)*x3D(L)+sin(288)*x3D(M))*(x(X)-x(O))+(y3D(K)+cos(288)*y3D(L)+sin(288)*y3D(M))*(x(Y)-x(O))+(z3D(K)+cos(288)*z3D(L)+sin(288)*z3D(M))*(x(Z)-x(O))" actx="2.384599127138305" y="y(O)+(x3D(K)+cos(288)*x3D(L)+sin(288)*x3D(M))*(y(X)-y(O))+(y3D(K)+cos(288)*y3D(L)+sin(288)*y3D(M))*(y(Y)-y(O))+(z3D(K)+cos(288)*z3D(L)+sin(288)*z3D(M))*(y(Z)-y(O))" acty="-0.721635420535101" shape="circle" is3D="true" x3D="x3D(P10)+cos(288)*x3D(P11)+sin(288)*x3D(P12)" actx3D="-2.9021107656685836" y3D="y3D(P10)+cos(288)*y3D(P11)+sin(288)*y3D(P12)" acty3D="-3.845845330265294" z3D="z3D(P10)+cos(288)*z3D(P11)+sin(288)*z3D(P12)" actz3D="-3.865163306023548" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="24" color="5" point1="C" point2="P13" point3="P14" point4="P15" point5="P16">Conique passant par C, P13, P14, P15, P16</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcircle2" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="l1">l1</Parameter>
<Parameter name="P">P</Parameter>
<Parameter name="E2">E2</Parameter>
<Objects>
<Expression name="E2" n="0" x="0.0" y="0.0" value="2" prompt="Valeur">Expression &quot;2&quot; à 0.0, 0.0</Expression>
<Point name="O" n="1" x="0.7990465586763337" y="-0.7302628017887207">Point</Point>
<Point name="X" n="2" x="-0.18982964415605608" y="-0.7993510216457196">Point</Point>
<Point name="Y" n="3" x="0.9477874591435368" y="-1.1895829773291575">Point</Point>
<Point name="Z" n="4" x="0.7990465586763337" y="0.15531711793535108">Point</Point>
<Point name="T" n="5" x="-2.5087144238116856" y="1.1328437948422683">Point</Point>
<Point name="P1" n="6" x="0.7981832351912815" y="0.843769867896833">Point</Point>
<Point name="P" n="7" x="-0.8877709467800874" y="2.4790496334151872">Point</Point>
<Line name="l1" n="8" color="5" type="thin" mainparameter="true" from="T" to="P1">Droite passant par T et P1</Line>
<Point name="P2" n="9" hidden="super" showname="true" x="x(O)+(x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(B)-z3D(A))*(x(Z)-x(O))" actx="4.1059442176793" y="y(O)+(x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(B)-z3D(A))*(y(Z)-y(O))" acty="-1.0193367287341562" shape="dcross" is3D="true" x3D="x3D(P1)-x3D(T)" actx3D="-3.660710860675136" y3D="y3D(P1)-y3D(T)" acty3D="-2.1049502563539106" z3D="z3D(P1)-z3D(T)" actz3D="-1.7037785200043254" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="10" hidden="super" showname="true" x="x(O)+(x3D(D)/d3D(O,D))*(x(X)-x(O))+(y3D(D)/d3D(O,D))*(x(Y)-x(O))+(z3D(D)/d3D(O,D))*(x(Z)-x(O))" actx="1.5252765362237939" y="y(O)+(x3D(D)/d3D(O,D))*(y(X)-y(O))+(y3D(D)/d3D(O,D))*(y(Y)-y(O))+(z3D(D)/d3D(O,D))*(y(Z)-y(O))" acty="-0.7937465176828603" shape="dcross" is3D="true" x3D="x3D(P2)/d3D(O,P2)" actx3D="-0.8039311282942435" y3D="y3D(P2)/d3D(O,P2)" acty3D="-0.46226951512957287" z3D="z3D(P2)/d3D(O,P2)" actz3D="-0.3741679253241976" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="11" hidden="super" showname="true" x="x(O)+(if(x3D(E)==0,0,-y3D(E)/x3D(E)))*(x(X)-x(O))+(if(x3D(E)==0,if(y3D(E)==0,1,-z3D(E)/y3D(E)),1))*(x(Y)-x(O))+(if(x3D(E)==0,if(y3D(E)==0,-y3D(E)/z3D(E),1),0))*(x(Z)-x(O))" actx="1.5164024893593973" y="y(O)+(if(x3D(E)==0,0,-y3D(E)/x3D(E)))*(y(X)-y(O))+(if(x3D(E)==0,if(y3D(E)==0,1,-z3D(E)/y3D(E)),1))*(y(Y)-y(O))+(if(x3D(E)==0,if(y3D(E)==0,-y3D(E)/z3D(E),1),0))*(y(Z)-y(O))" acty="-1.1498564674697571" shape="dcross" is3D="true" x3D="if(x3D(P3)==0,0,-y3D(P3)/x3D(P3))" actx3D="-0.5750113397280713" y3D="if(x3D(P3)==0,if(y3D(P3)==0,1,-z3D(P3)/y3D(P3)),1)" acty3D="1.0" z3D="if(x3D(P3)==0,if(y3D(P3)==0,-y3D(P3)/z3D(P3),1),0)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="12" hidden="super" showname="true" x="x(O)+(x3D(F)/d3D(O,F))*(x(X)-x(O))+(y3D(F)/d3D(O,F))*(x(Y)-x(O))+(z3D(F)/d3D(O,F))*(x(Z)-x(O))" actx="1.420923889168291" y="y(O)+(x3D(F)/d3D(O,F))*(y(X)-y(O))+(y3D(F)/d3D(O,F))*(y(Y)-y(O))+(z3D(F)/d3D(O,F))*(y(Z)-y(O))" acty="-1.0940094128421702" shape="dcross" is3D="true" x3D="x3D(P4)/d3D(O,P4)" actx3D="-0.49847851207169136" y3D="y3D(P4)/d3D(O,P4)" acty3D="0.8669020550228226" z3D="z3D(P4)/d3D(O,P4)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="13" hidden="super" showname="true" x="x(O)+(y3D(E)*z3D(G)-z3D(E)*y3D(G))*(x(X)-x(O))+(z3D(E)*x3D(G)-x3D(E)*z3D(G))*(x(Y)-x(O))+(x3D(E)*y3D(G)-y3D(E)*x3D(G))*(x(Z)-x(O))" actx="0.5060301674426474" y="y(O)+(y3D(E)*z3D(G)-z3D(E)*y3D(G))*(y(X)-y(O))+(z3D(E)*x3D(G)-x3D(E)*z3D(G))*(y(Y)-y(O))+(x3D(E)*y3D(G)-y3D(E)*x3D(G))*(y(Z)-y(O))" acty="-1.6595949387362188" shape="dcross" is3D="true" x3D="y3D(P3)*z3D(P5)-z3D(P3)*y3D(P5)" actx3D="0.3243669433871729" y3D="z3D(P3)*x3D(P5)-x3D(P3)*z3D(P5)" acty3D="0.18651467068055777" z3D="x3D(P3)*y3D(P5)-y3D(P3)*x3D(P5)" actz3D="-0.9273609672929879" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="14" hidden="super" showname="true" x="x(O)+(x3D(P)+(cos(72)*x3D(G)+sin(72)*x3D(H))*E2)*(x(X)-x(O))+(y3D(P)+(cos(72)*y3D(G)+sin(72)*y3D(H))*E2)*(x(Y)-x(O))+(z3D(P)+(cos(72)*z3D(G)+sin(72)*z3D(H))*E2)*(x(Z)-x(O))" actx="-1.0607799162311813" y="y(O)+(x3D(P)+(cos(72)*x3D(G)+sin(72)*x3D(H))*E2)*(y(X)-y(O))+(y3D(P)+(cos(72)*y3D(G)+sin(72)*y3D(H))*E2)*(y(Y)-y(O))+(z3D(P)+(cos(72)*z3D(G)+sin(72)*z3D(H))*E2)*(y(Z)-y(O))" acty="0.48654709519873107" shape="dcross" is3D="true" x3D="x3D(P)+(cos(72)*x3D(P5)+sin(72)*x3D(P6))*E2" actx3D="1.7360936299929814" y3D="y3D(P)+(cos(72)*y3D(P5)+sin(72)*y3D(P6))*E2" acty3D="-0.9617045336504236" z3D="z3D(P)+(cos(72)*z3D(P5)+sin(72)*z3D(P6))*E2" actz3D="1.0106634085078015" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="15" hidden="super" showname="true" x="x(O)+(x3D(P)+(cos(288)*x3D(G)+sin(288)*x3D(H))*E2)*(x(X)-x(O))+(y3D(P)+(cos(288)*y3D(G)+sin(288)*y3D(H))*E2)*(x(Y)-x(O))+(z3D(P)+(cos(288)*z3D(G)+sin(288)*z3D(H))*E2)*(x(Z)-x(O))" actx="0.05392067682516816" y="y(O)+(x3D(P)+(cos(288)*x3D(G)+sin(288)*x3D(H))*E2)*(y(X)-y(O))+(y3D(P)+(cos(288)*y3D(G)+sin(288)*y3D(H))*E2)*(y(Y)-y(O))+(z3D(P)+(cos(288)*z3D(G)+sin(288)*z3D(H))*E2)*(y(Z)-y(O))" acty="4.021936633784404" shape="dcross" is3D="true" x3D="x3D(P)+(cos(288)*x3D(P5)+sin(288)*x3D(P6))*E2" actx3D="0.5021284492765338" y3D="y3D(P)+(cos(288)*y3D(P5)+sin(288)*y3D(P6))*E2" acty3D="-1.6712485053919806" z3D="z3D(P)+(cos(288)*z3D(P5)+sin(288)*z3D(P6))*E2" actz3D="4.538554172114893" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="16" hidden="super" showname="true" x="x(O)+(x3D(P)+(cos(216)*x3D(G)+sin(216)*x3D(H))*E2)*(x(X)-x(O))+(y3D(P)+(cos(216)*y3D(G)+sin(216)*y3D(H))*E2)*(x(Y)-x(O))+(z3D(P)+(cos(216)*z3D(G)+sin(216)*z3D(H))*E2)*(x(Z)-x(O))" actx="-1.549528177454881" y="y(O)+(x3D(P)+(cos(216)*x3D(G)+sin(216)*x3D(H))*E2)*(y(X)-y(O))+(y3D(P)+(cos(216)*y3D(G)+sin(216)*y3D(H))*E2)*(y(Y)-y(O))+(z3D(P)+(cos(216)*z3D(G)+sin(216)*z3D(H))*E2)*(y(Z)-y(O))" acty="4.160099462550633" shape="dcross" is3D="true" x3D="x3D(P)+(cos(216)*x3D(P5)+sin(216)*x3D(P6))*E2" actx3D="1.8524266666416735" y3D="y3D(P)+(cos(216)*y3D(P5)+sin(216)*y3D(P6))*E2" acty3D="-3.4741895899109347" z3D="z3D(P)+(cos(216)*z3D(P5)+sin(216)*z3D(P6))*E2" actz3D="3.8647869905643484" fixed="true" fixed3D="true">Point</Point>
<Point name="P10" n="17" hidden="super" showname="true" x="x(O)+(x3D(P)+(cos(144)*x3D(G)+sin(144)*x3D(H))*E2)*(x(X)-x(O))+(y3D(P)+(cos(144)*y3D(G)+sin(144)*y3D(H))*E2)*(x(Y)-x(O))+(z3D(P)+(cos(144)*z3D(G)+sin(144)*z3D(H))*E2)*(x(Z)-x(O))" actx="-2.2384510312433696" y="y(O)+(x3D(P)+(cos(144)*x3D(G)+sin(144)*x3D(H))*E2)*(y(X)-y(O))+(y3D(P)+(cos(144)*y3D(G)+sin(144)*y3D(H))*E2)*(y(Y)-y(O))+(z3D(P)+(cos(144)*z3D(G)+sin(144)*z3D(H))*E2)*(y(Z)-y(O))" acty="1.97510856423388" shape="dcross" is3D="true" x3D="x3D(P)+(cos(144)*x3D(P5)+sin(144)*x3D(P6))*E2" actx3D="2.6150590892583443" y3D="y3D(P)+(cos(144)*y3D(P5)+sin(144)*y3D(P6))*E2" acty3D="-3.035667298862058" z3D="z3D(P)+(cos(144)*z3D(P5)+sin(144)*z3D(P6))*E2" actz3D="1.6844305900583454" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="18" hidden="super" showname="true" x="x(O)+(x3D(P)+x3D(G)*E2)*(x(X)-x(O))+(y3D(P)+y3D(G)*E2)*(x(Y)-x(O))+(z3D(P)+z3D(G)*E2)*(x(Z)-x(O))" actx="0.355983714203827" y="y(O)+(x3D(P)+x3D(G)*E2)*(y(X)-y(O))+(y3D(P)+y3D(G)*E2)*(y(Y)-y(O))+(z3D(P)+z3D(G)*E2)*(y(Z)-y(O))" acty="1.7515564113082878" shape="dcross" is3D="true" x3D="x3D(P)+x3D(P5)*E2" actx3D="0.4302306786131549" y3D="y3D(P)+y3D(P5)*E2" acty3D="-0.11844734439679261" z3D="z3D(P)+z3D(P5)*E2" actz3D="2.774608790311347" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="19" color="5" point1="P11" point2="P7" point3="P10" point4="P9" point5="P8">Conique passant par P11, P7, P10, P9, P8</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dcircle3pts" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="O" n="0" mainparameter="true" x="0.5624500933810208" y="-1.0542367312619834">Point</Point>
<Point name="X" n="1" mainparameter="true" x="-0.3594302777717885" y="-1.2103522442652281">Point</Point>
<Point name="Y" n="2" mainparameter="true" x="0.9499247135604056" y="-1.4256670639209679">Point</Point>
<Point name="Z" n="3" mainparameter="true" x="0.5624500933810208" y="-0.138994984737837">Point</Point>
<Point name="A" n="4" mainparameter="true" x="-2.439458095730769" y="-0.19472508729244997">Point</Point>
<Point name="B" n="5" mainparameter="true" x="-1.190755791444892" y="1.92159957324516">Point</Point>
<Point name="C" n="6" mainparameter="true" x="2.5155111875521206" y="-0.5039233061681723">Point</Point>
<Midpoint name="M" n="7" hidden="super" showname="true" first="B" second="A" shape="dcross">Milieu de B et A</Midpoint>
<Midpoint name="P1" n="8" hidden="super" showname="true" first="B" second="C" shape="dcross">Milieu de B et C</Midpoint>
<Expression name="E1" n="9" type="thick" hidden="super" showname="true" showvalue="true" x="3.9650536198523643" y="2.313764047482537" value="((x3D(C)-x3D(B))*(x3D(B)-x3D(A))+(y3D(C)-y3D(B))*(y3D(B)-y3D(A))+(z3D(C)-z3D(B))*(z3D(B)-z3D(A)))/(d3D(A,B)*d3D(A,B))" prompt="Valeur">Expression &quot;((x3D(C)-x3D(B))*(x3D(B)-x3D(A))+(y3D(C)-y3D(B))*(y3D(B)-y3D(A))+(z3D(C)-z3D(B))*(z3D(B)-z3D(A)))/(d3D(A,B)*d3D(A,B))&quot; à 3.96505, 2.31376</Expression>
<Expression name="E2" n="10" type="thick" hidden="super" showname="true" showvalue="true" x="3.9881221183369213" y="1.7947228315801271" value="((x3D(B)-x3D(A))*(x3D(C)-x3D(B))+(y3D(B)-y3D(A))*(y3D(C)-y3D(B))+(z3D(B)-z3D(A))*(z3D(C)-z3D(B)))/(d3D(B,C)*d3D(B,C))" prompt="Valeur">Expression &quot;((x3D(B)-x3D(A))*(x3D(C)-x3D(B))+(y3D(B)-y3D(A))*(y3D(C)-y3D(B))+(z3D(B)-z3D(A))*(z3D(C)-z3D(B)))/(d3D(B,C)*d3D(B,C))&quot; à 3.98812, 1.79472</Expression>
<Point name="P2" n="11" hidden="super" showname="true" x="x(O)+(x3D(C)-x3D(B)+E1*(x3D(A)-x3D(B)))*(x(X)-x(O))+(y3D(C)-y3D(B)+E1*(y3D(A)-y3D(B)))*(x(Y)-x(O))+(z3D(C)-z3D(B)+E1*(z3D(A)-z3D(B)))*(x(Z)-x(O))" actx="4.446244373291605" y="y(O)+(x3D(C)-x3D(B)+E1*(x3D(A)-x3D(B)))*(y(X)-y(O))+(y3D(C)-y3D(B)+E1*(y3D(A)-y3D(B)))*(y(Y)-y(O))+(z3D(C)-z3D(B)+E1*(z3D(A)-z3D(B)))*(y(Z)-y(O))" acty="-3.178882929698151" shape="dcross" is3D="true" x3D="x3D(C)-x3D(B)+E1*(x3D(A)-x3D(B))" actx3D="-4.05412467776867" y3D="y3D(C)-y3D(B)+E1*(y3D(A)-y3D(B))" acty3D="0.3777700774354521" z3D="z3D(C)-z3D(B)+E1*(z3D(A)-z3D(B))" actz3D="-2.859618998690013" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="12" hidden="super" showname="true" x="x(O)+(x3D(A)-x3D(B)+E2*(x3D(C)-x3D(B)))*(x(X)-x(O))+(y3D(A)-y3D(B)+E2*(y3D(C)-y3D(B)))*(x(Y)-x(O))+(z3D(A)-z3D(B)+E2*(z3D(C)-z3D(B)))*(x(Z)-x(O))" actx="-0.8146487359112188" y="y(O)+(x3D(A)-x3D(B)+E2*(x3D(C)-x3D(B)))*(y(X)-y(O))+(y3D(A)-y3D(B)+E2*(y3D(C)-y3D(B)))*(y(Y)-y(O))+(z3D(A)-z3D(B)+E2*(z3D(C)-z3D(B)))*(y(Z)-y(O))" acty="-3.0865337945420492" shape="dcross" is3D="true" x3D="x3D(A)-x3D(B)+E2*(x3D(C)-x3D(B))" actx3D="1.5598064879248197" y3D="y3D(A)-y3D(B)+E2*(y3D(C)-y3D(B))" acty3D="0.15705842796691905" z3D="z3D(A)-z3D(B)+E2*(z3D(C)-z3D(B))" actz3D="-1.8907035388814506" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="13" hidden="super" showname="true" x="x(O)+(x3D(D)+x3D(F))*(x(X)-x(O))+(y3D(D)+y3D(F))*(x(Y)-x(O))+(z3D(D)+z3D(F))*(x(Z)-x(O))" actx="2.068687336322753" y="y(O)+(x3D(D)+x3D(F))*(y(X)-y(O))+(y3D(D)+y3D(F))*(y(Y)-y(O))+(z3D(D)+z3D(F))*(y(Z)-y(O))" acty="-1.261208955459813" shape="dcross" is3D="true" x3D="x3D(M)+x3D(P2)" actx3D="-1.6410160483924279" y3D="y3D(M)+y3D(P2)" acty3D="-0.016990121352845833" z3D="z3D(M)+z3D(P2)" actz3D="-0.512947464042266" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="14" hidden="super" showname="true" x="x(O)+(x3D(E)+x3D(G))*(x(X)-x(O))+(y3D(E)+y3D(G))*(x(Y)-x(O))+(z3D(E)+z3D(G))*(x(Z)-x(O))" actx="-0.7147211312386254" y="y(O)+(x3D(E)+x3D(G))*(y(X)-y(O))+(y3D(E)+y3D(G))*(y(Y)-y(O))+(z3D(E)+z3D(G))*(y(Z)-y(O))" acty="-1.3234589297415718" shape="dcross" is3D="true" x3D="x3D(P1)+x3D(P3)" actx3D="1.3340547492153356" y3D="y3D(P1)+y3D(P3)" acty3D="-0.12215596792618738" z3D="z3D(P1)+z3D(P3)" actz3D="-0.11617475835027369" fixed="true" fixed3D="true">Point</Point>
<Line name="l1" n="15" color="5" type="thin" hidden="super" from="M" to="P4">Droite passant par M et P4</Line>
<Line name="l2" n="16" color="5" type="thin" hidden="super" from="P1" to="P5">Droite passant par P1 et P5</Line>
<Intersection name="P6" n="17" showname="false" first="l1" second="l2" shape="dcross">Intersection entre l1 et l2</Intersection>
<Point name="P7" n="18" hidden="super" showname="true" x="x(O)+(x3D(J)+(x3D(E)-x3D(J))*d3D(J,A)/d3D(J,E))*(x(X)-x(O))+(y3D(J)+(y3D(E)-y3D(J))*d3D(J,A)/d3D(J,E))*(x(Y)-x(O))+(z3D(J)+(z3D(E)-z3D(J))*d3D(J,A)/d3D(J,E))*(x(Z)-x(O))" actx="1.587873561129074" y="y(O)+(x3D(J)+(x3D(E)-x3D(J))*d3D(J,A)/d3D(J,E))*(y(X)-y(O))+(y3D(J)+(y3D(E)-y3D(J))*d3D(J,A)/d3D(J,E))*(y(Y)-y(O))+(z3D(J)+(z3D(E)-z3D(J))*d3D(J,A)/d3D(J,E))*(y(Z)-y(O))" acty="2.0746678652889647" shape="dcross" is3D="true" x3D="x3D(P6)+(x3D(P1)-x3D(P6))*d3D(P6,A)/d3D(P6,P1)" actx3D="-1.2740384855035531" y3D="y3D(P6)+(y3D(P1)-y3D(P6))*d3D(P6,A)/d3D(P6,P1)" acty3D="-0.38476740505456575" z3D="z3D(P6)+(z3D(P1)-z3D(P6))*d3D(P6,A)/d3D(P6,P1)" actz3D="3.045198877924437" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="19" hidden="super" showname="true" x="x(O)+(x3D(J)+(x3D(D)-x3D(J))*d3D(J,A)/d3D(J,D))*(x(X)-x(O))+(y3D(J)+(y3D(D)-y3D(J))*d3D(J,A)/d3D(J,D))*(x(Y)-x(O))+(z3D(J)+(z3D(D)-z3D(J))*d3D(J,A)/d3D(J,D))*(x(Z)-x(O))" actx="-2.0460389970515322" y="y(O)+(x3D(J)+(x3D(D)-x3D(J))*d3D(J,A)/d3D(J,D))*(y(X)-y(O))+(y3D(J)+(y3D(D)-y3D(J))*d3D(J,A)/d3D(J,D))*(y(Y)-y(O))+(z3D(J)+(z3D(D)-z3D(J))*d3D(J,A)/d3D(J,D))*(y(Z)-y(O))" acty="0.9897696061570351" shape="dcross" is3D="true" x3D="x3D(P6)+(x3D(M)-x3D(P6))*d3D(P6,A)/d3D(P6,M)" actx3D="2.6541686004663028" y3D="y3D(P6)+(y3D(M)-y3D(P6))*d3D(P6,A)/d3D(P6,M)" acty3D="-0.41722256765540466" z3D="z3D(P6)+(z3D(M)-z3D(P6))*d3D(P6,A)/d3D(P6,M)" actz3D="2.5167056919426347" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="20" color="5" point1="A" point2="P8" point3="B" point4="P7" point5="C">Conique passant par A, P8, B, P7, C</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dplandroite" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Parameter name="l2">l2</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.46951465589668023" y="0.19974651181101305">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.11111079550150854" y="-0.020271685559660524">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.2836854519761327" y="0.0428406564556994">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.46951465589668023" y="1.1625406462867984">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="-2.200067256413" y="0.6483197411832289">Point</Point>
<Point name="B" n="5" parameter="true" mainparameter="true" x="4.540368636293942" y="1.3408055039860154">Point</Point>
<Point name="C" n="6" parameter="true" mainparameter="true" x="0.23596931121541664" y="2.502258833488571">Point</Point>
<Point name="G" n="7" parameter="true" x="1.75077614133866" y="-3.5993898524236307">Point</Point>
<Point name="P1" n="8" parameter="true" x="1.75077614133866" y="0.25178668547951033">Point</Point>
<Line name="l2" n="9" color="5" type="thin" parameter="true" mainparameter="true" from="G" to="P1">Droite passant par G et P1</Line>
<Expression name="E1" n="10" type="thick" hidden="super" showname="true" showvalue="true" x="5.441490148910955" y="5.993464648938691" value="-(x3D(B)*(y3D(A)*(z3D(G)-z3D(C))+y3D(C)*(z3D(A)-z3D(G))+(z3D(C)-z3D(A))*y3D(G))+x3D(A)*(y3D(C)*(z3D(G)-z3D(B))+y3D(B)*(z3D(C)-z3D(G))+(z3D(B)-z3D(C))*y3D(G))+x3D(C)*(y3D(B)*(z3D(G)-z3D(A))+y3D(A)*(z3D(B)-z3D(G))+(z3D(A)-z3D(B))*y3D(G))+(y3D(A)*(z3D(C)-z3D(B))+y3D(B)*(z3D(A)-z3D(C))+(z3D(B)-z3D(A))*y3D(C))*x3D(G))/(x3D(A)*(y3D(C)*(z3D(P1)-z3D(G))+y3D(B)*(z3D(G)-z3D(P1))+(z3D(B)-z3D(C))*y3D(P1)+(z3D(C)-z3D(B))*y3D(G))+x3D(C)*(y3D(B)*(z3D(P1)-z3D(G))+y3D(A)*(z3D(G)-z3D(P1))+(z3D(A)-z3D(B))*y3D(P1)+(z3D(B)-z3D(A))*y3D(G))+x3D(B)*(y3D(A)*(z3D(P1)-z3D(G))+y3D(C)*(z3D(G)-z3D(P1))+(z3D(C)-z3D(A))*y3D(P1)+(z3D(A)-z3D(C))*y3D(G))+(y3D(A)*(z3D(C)-z3D(B))+y3D(B)*(z3D(A)-z3D(C))+(z3D(B)-z3D(A))*y3D(C))*x3D(P1)+(y3D(B)*(z3D(C)-z3D(A))+y3D(A)*(z3D(B)-z3D(C))+(z3D(A)-z3D(B))*y3D(C))*x3D(G))" prompt="Valeur">Expression &quot;-(x3D(B)*(y3D(A)*(z3D(G)-z3D(C))+y3D(C)*(z3D(A)-z3D(G))+(z3D(C)-z3D(A))*y3D(G))+x3D(A)*(y3D(C)*(z3D(G)-z3D(B))+y3D(B)*(z3D(C)-z3D(G))+(z3D(B)-z3D(C))*y3D(G))+x3D(C)*(y3D(B)*(z3D(G)-z3D(A))+y3D(A)*(z3D(B)-z3D(G))+(z3D(A)-z3D(B))*y3D(G))+(y3D(A)*(z3D(C)-z3D(B))+y3D(B)*(z3D(A)-z3D(C))+(z3D(B)-z3D(A))*y3D(C))*x3D(G))/(x3D(A)*(y3D(C)*(z3D(P1)-z3D(G))+y3D(B)*(z3D(G)-z3D(P1))+(z3D(B)-z3D(C))*y3D(P1)+(z3D(C)-z3D(B))*y3D(G))+x3D(C)*(y3D(B)*(z3D(P1)-z3D(G))+y3D(A)*(z3D(G)-z3D(P1))+(z3D(A)-z3D(B))*y3D(P1)+(z3D(B)-z3D(A))*y3D(G))+x3D(B)*(y3D(A)*(z3D(P1)-z3D(G))+y3D(C)*(z3D(G)-z3D(P1))+(z3D(C)-z3D(A))*y3D(P1)+(z3D(A)-z3D(C))*y3D(G))+(y3D(A)*(z3D(C)-z3D(B))+y3D(B)*(z3D(A)-z3D(C))+(z3D(B)-z3D(A))*y3D(C))*x3D(P1)+(y3D(B)*(z3D(C)-z3D(A))+y3D(A)*(z3D(B)-z3D(C))+(z3D(A)-z3D(B))*y3D(C))*x3D(G))&quot; à 5.44149, 5.99346</Expression>
<Point name="P2" n="11" showname="true" target="true" x="x(O)+(x3D(D)+E1*(x3D(E)-x3D(D)))*(x(X)-x(O))+(y3D(D)+E1*(y3D(E)-y3D(D)))*(x(Y)-x(O))+(z3D(D)+E1*(z3D(E)-z3D(D)))*(x(Z)-x(O))" actx="1.75077614133866" y="y(O)+(x3D(D)+E1*(x3D(E)-x3D(D)))*(y(X)-y(O))+(y3D(D)+E1*(y3D(E)-y3D(D)))*(y(Y)-y(O))+(z3D(D)+E1*(z3D(E)-z3D(D)))*(y(Z)-y(O))" acty="1.2145808199552957" shape="dcross" is3D="true" x3D="x3D(G)+E1*(x3D(P1)-x3D(G))" actx3D="2.0" y3D="y3D(G)+E1*(y3D(P1)-y3D(G))" acty3D="3.0" z3D="z3D(G)+E1*(z3D(P1)-z3D(G))" actz3D="2.0" fixed="true" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dplanplan" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Parameter name="D">D</Parameter>
<Parameter name="E">E</Parameter>
<Parameter name="F">F</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.49543290118470784" y="0.3299515343483304">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.17791366441597922" y="-0.042932628175987364">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.2347598941950806" y="-0.009654989661155022">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.49543290118470784" y="1.1934472263982288">Point</Point>
<Point name="A" n="4" parameter="true" mainparameter="true" x="-1.3402640311584215" y="1.5726828731040934">Point</Point>
<Point name="B" n="5" parameter="true" mainparameter="true" x="0.46220000282066254" y="2.358115626433309">Point</Point>
<Point name="C" n="6" parameter="true" mainparameter="true" x="-0.0026889109205492367" y="1.175946871597377">Point</Point>
<Point name="D" n="7" parameter="true" mainparameter="true" x="-1.005870251098954" y="-0.9291062233746643">Point</Point>
<Point name="E" n="8" parameter="true" mainparameter="true" x="0.8863091872863297" y="-0.127666031001207">Point</Point>
<Point name="F" n="9" parameter="true" mainparameter="true" x="1.7997751230585346" y="-0.674518721455458">Point</Point>
<Point name="N" n="10" hidden="super" showname="true" x="x(O)+(x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(B)-z3D(A))*(x(Z)-x(O))" actx="2.297896935163792" y="y(O)+(x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(B)-z3D(A))*(y(Z)-y(O))" acty="1.1153842876775462" shape="dcross" is3D="true" x3D="x3D(B)-x3D(A)" actx3D="-3.357585423196813" y3D="y3D(B)-y3D(A)" acty3D="-0.6199619164114416" z3D="z3D(B)-z3D(A)" actz3D="-0.7841391601465064" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="11" hidden="super" showname="true" x="x(O)+(x3D(C)-x3D(A))*(x(X)-x(O))+(y3D(C)-y3D(A))*(x(Y)-x(O))+(z3D(C)-z3D(A))*(x(Z)-x(O))" actx="1.8330080214225801" y="y(O)+(x3D(C)-x3D(A))*(y(X)-y(O))+(y3D(C)-y3D(A))*(y(Y)-y(O))+(z3D(C)-z3D(A))*(y(Z)-y(O))" acty="-0.06678446715838571" shape="dcross" is3D="true" x3D="x3D(C)-x3D(A)" actx3D="-1.5573626617452265" y3D="y3D(C)-y3D(A)" acty3D="0.39080180121724006" z3D="z3D(C)-z3D(A)" actz3D="-0.9782712755460746" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="12" hidden="super" showname="true" x="x(O)+(x3D(D)-x3D(E))*(x(X)-x(O))+(y3D(D)-y3D(E))*(x(Y)-x(O))+(z3D(D)-z3D(E))*(x(Z)-x(O))" actx="-1.3967465372005758" y="y(O)+(x3D(D)-x3D(E))*(y(X)-y(O))+(y3D(D)-y3D(E))*(y(Y)-y(O))+(z3D(D)-z3D(E))*(y(Z)-y(O))" acty="-0.4714886580251267" shape="dcross" is3D="true" x3D="x3D(D)-x3D(E)" actx3D="2.113996384083848" y3D="y3D(D)-y3D(E)" acty3D="-0.6339917761714635" z3D="z3D(D)-z3D(E)" actz3D="-0.26458981390946334" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="13" hidden="super" showname="true" x="x(O)+(x3D(D)-x3D(F))*(x(X)-x(O))+(y3D(D)-y3D(F))*(x(Y)-x(O))+(z3D(D)-z3D(F))*(x(Z)-x(O))" actx="-2.3102124729727804" y="y(O)+(x3D(D)-x3D(F))*(y(X)-y(O))+(y3D(D)-y3D(F))*(y(Y)-y(O))+(z3D(D)-z3D(F))*(y(Z)-y(O))" acty="0.07536403242912426" shape="dcross" is3D="true" x3D="x3D(D)-x3D(F)" actx3D="4.378015630530897" y3D="y3D(D)-y3D(F)" acty3D="0.1924404440142249" z3D="z3D(D)-z3D(F)" actz3D="1.671414499861561" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="14" hidden="super" showname="true" x="x(O)+(x3D(A)-x3D(D))*(x(X)-x(O))+(y3D(A)-y3D(D))*(x(Y)-x(O))+(z3D(A)-z3D(D))*(x(Z)-x(O))" actx="0.16103912112524021" y="y(O)+(x3D(A)-x3D(D))*(y(X)-y(O))+(y3D(A)-y3D(D))*(y(Y)-y(O))+(z3D(A)-z3D(D))*(y(Z)-y(O))" acty="2.8317406308270883" shape="dcross" is3D="true" x3D="x3D(A)-x3D(D)" actx3D="1.4415326952584278" y3D="y3D(A)-y3D(D)" acty3D="0.8605898547044797" z3D="z3D(A)-z3D(D)" actz3D="3.858242453470301" fixed="true" fixed3D="true">Point</Point>
<Expression name="E1" n="15" type="thick" hidden="super" showname="true" showvalue="true" x="3.368646052185892" y="3.7078833557766697" value="-((x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P4)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P4)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P4))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))" prompt="Valeur">Expression &quot;-((x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P4)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P4)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P4))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))&quot; à 3.36865, 3.70788</Expression>
<Expression name="E2" n="16" type="thick" hidden="super" showname="true" showvalue="true" x="3.4722201084274573" y="3.112332532387669" value="-((x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P4)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P4)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P4)+(x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P3)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P3)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P3))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))" prompt="Valeur">Expression &quot;-((x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P4)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P4)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P4)+(x3D(P1)*y3D(P2)-y3D(P1)*x3D(P2))*z3D(P3)+(z3D(P1)*x3D(P2)-x3D(P1)*z3D(P2))*y3D(P3)+(y3D(P1)*z3D(P2)-z3D(P1)*y3D(P2))*x3D(P3))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))&quot; à 3.47222, 3.11233</Expression>
<Expression name="E3" n="17" type="thick" hidden="super" showname="true" showvalue="true" x="4.054824174786264" y="2.2448998113645566" value="((x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P4)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P4)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P4))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))" prompt="Valeur">Expression &quot;((x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P4)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P4)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P4))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))&quot; à 4.05482, 2.2449</Expression>
<Expression name="E4" n="18" type="thick" hidden="super" showname="true" showvalue="true" x="4.624481484114872" y="1.5457749317339906" value="((x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P4)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P4)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P4)+(x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P3)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P3)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P3))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))" prompt="Valeur">Expression &quot;((x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P4)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P4)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P4)+(x3D(N)*y3D(P2)-y3D(N)*x3D(P2))*z3D(P3)+(z3D(N)*x3D(P2)-x3D(N)*z3D(P2))*y3D(P3)+(y3D(N)*z3D(P2)-z3D(N)*y3D(P2))*x3D(P3))/((x3D(N)*y3D(P1)-y3D(N)*x3D(P1))*z3D(P2)+(z3D(N)*x3D(P1)-x3D(N)*z3D(P1))*y3D(P2)+(y3D(N)*z3D(P1)-z3D(N)*y3D(P1))*x3D(P2))&quot; à 4.62448, 1.54577</Expression>
<Point name="P5" n="19" hidden="super" showname="true" x="x(O)+(x3D(A)+a1*(x3D(B)-x3D(A))+b1*(x3D(C)-x3D(A)))*(x(X)-x(O))+(y3D(A)+a1*(y3D(B)-y3D(A))+b1*(y3D(C)-y3D(A)))*(x(Y)-x(O))+(z3D(A)+a1*(z3D(B)-z3D(A))+b1*(z3D(C)-z3D(A)))*(x(Z)-x(O))" actx="3.5499478631704395" y="y(O)+(x3D(A)+a1*(x3D(B)-x3D(A))+b1*(x3D(C)-x3D(A)))*(y(X)-y(O))+(y3D(A)+a1*(y3D(B)-y3D(A))+b1*(y3D(C)-y3D(A)))*(y(Y)-y(O))+(z3D(A)+a1*(z3D(B)-z3D(A))+b1*(z3D(C)-z3D(A)))*(y(Z)-y(O))" acty="1.0005288164365482" shape="dcross" is3D="true" x3D="x3D(A)+E1*(x3D(B)-x3D(A))+E3*(x3D(C)-x3D(A))" actx3D="-4.61433001389805" y3D="y3D(A)+E1*(y3D(B)-y3D(A))+E3*(y3D(C)-y3D(A))" acty3D="-0.07104881320078316" z3D="z3D(A)+E1*(z3D(B)-z3D(A))+E3*(z3D(C)-z3D(A))" actz3D="-1.2439690795574059" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="20" hidden="super" showname="true" x="x(O)+(x3D(A)+a2*(x3D(B)-x3D(A))+b2*(x3D(C)-x3D(A)))*(x(X)-x(O))+(y3D(A)+a2*(y3D(B)-y3D(A))+b2*(y3D(C)-y3D(A)))*(x(Y)-x(O))+(z3D(A)+a2*(z3D(B)-z3D(A))+b2*(z3D(C)-z3D(A)))*(x(Z)-x(O))" actx="6.457650240790709" y="y(O)+(x3D(A)+a2*(x3D(B)-x3D(A))+b2*(x3D(C)-x3D(A)))*(y(X)-y(O))+(y3D(A)+a2*(y3D(B)-y3D(A))+b2*(y3D(C)-y3D(A)))*(y(Y)-y(O))+(z3D(A)+a2*(z3D(B)-z3D(A))+b2*(z3D(C)-z3D(A)))*(y(Z)-y(O))" acty="1.2983429716154844" shape="dcross" is3D="true" x3D="x3D(A)+E2*(x3D(B)-x3D(A))+E4*(x3D(C)-x3D(A))" actx3D="-9.106366615122331" y3D="y3D(A)+E2*(y3D(B)-y3D(A))+E4*(y3D(C)-y3D(A))" acty3D="-0.22929413830423395" z3D="z3D(A)+E2*(z3D(B)-z3D(A))+E4*(z3D(C)-z3D(A))" actz3D="-2.901112605426447" fixed="true" fixed3D="true">Point</Point>
<Line name="l1" n="21" color="5" target="true" from="P6" to="P5">Droite passant par P6 et P5</Line>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dsphererayon" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Objects>
<Point name="O" n="0" x="0.46872564496308566" y="0.2810960421746347">Point</Point>
<Point name="X" n="1" x="-0.2046209206376014" y="-0.04768789704438847">Point</Point>
<Point name="Y" n="2" x="1.2080526379734584" y="-0.01834593463776646">Point</Point>
<Point name="Z" n="3" x="0.46872564496308566" y="1.1767721129917037">Point</Point>
<Point name="A" n="4" x="-1.5708699337577685" y="1.2590713800679145">Point</Point>
<Circle name="c1" n="214" color="5" target="true" fixed="1" midpoint="A" filled="true" acute="true">Sphère de centre A de rayon 1</Circle>
<Point name="C" n="5" hidden="super" showname="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+1)*(x(Z)-x(O))" actx="-1.5708699337577685" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+1)*(y(Z)-y(O))" acty="2.154747450884983" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="1.2784877518064197" y3D="y3D(A)" acty3D="-1.5943286976171858" z3D="z3D(A)+1" actz3D="2.0281760005401788" fixed="true" fixed3D="true">Point</Point>
<Expression name="E1" n="6" type="thick" hidden="super" showname="true" showvalue="true" x="2.368451914259868" y="2.466151366624043" value="c1" prompt="Valeur">Expression &quot;c1&quot; à 2.36845, 2.46615</Expression>
<Point name="P10" n="7" hidden="super" showname="true" x="x(O)+(x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(B)-z3D(A))*(x(Z)-x(O))" actx="0.46872564496308566" y="y(O)+(x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(B)-z3D(A))*(y(Z)-y(O))" acty="1.1767721129917035" shape="dcross" is3D="true" x3D="x3D(C)-x3D(A)" actx3D="0.0" y3D="y3D(C)-y3D(A)" acty3D="0.0" z3D="z3D(C)-z3D(A)" actz3D="0.9999999999999998" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="8" hidden="super" showname="true" x="x(O)+(x3D(P12)/d3D(O,P12))*(x(X)-x(O))+(y3D(P12)/d3D(O,P12))*(x(Y)-x(O))+(z3D(P12)/d3D(O,P12))*(x(Z)-x(O))" actx="0.46872564496308566" y="y(O)+(x3D(P12)/d3D(O,P12))*(y(X)-y(O))+(y3D(P12)/d3D(O,P12))*(y(Y)-y(O))+(z3D(P12)/d3D(O,P12))*(y(Z)-y(O))" acty="1.1767721129917037" shape="dcross" is3D="true" x3D="x3D(P10)/d3D(O,P10)" actx3D="0.0" y3D="y3D(P10)/d3D(O,P10)" acty3D="0.0" z3D="z3D(P10)/d3D(O,P10)" actz3D="1.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="9" hidden="super" showname="true" x="x(O)+(if(x3D(P1)==0,0,-y3D(P1)/x3D(P1)))*(x(X)-x(O))+(if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1))*(x(Y)-x(O))+(if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0))*(x(Z)-x(O))" actx="1.2080526379734584" y="y(O)+(if(x3D(P1)==0,0,-y3D(P1)/x3D(P1)))*(y(X)-y(O))+(if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1))*(y(Y)-y(O))+(if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0))*(y(Z)-y(O))" acty="-0.01834593463776646" shape="dcross" is3D="true" x3D="if(x3D(P1)==0,0,-y3D(P1)/x3D(P1))" actx3D="0.0" y3D="if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1)" acty3D="1.0" z3D="if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0)" actz3D="-0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="10" hidden="super" showname="true" x="x(O)+(x3D(P2)/d3D(O,P2))*(x(X)-x(O))+(y3D(P2)/d3D(O,P2))*(x(Y)-x(O))+(z3D(P2)/d3D(O,P2))*(x(Z)-x(O))" actx="1.2080526379734584" y="y(O)+(x3D(P2)/d3D(O,P2))*(y(X)-y(O))+(y3D(P2)/d3D(O,P2))*(y(Y)-y(O))+(z3D(P2)/d3D(O,P2))*(y(Z)-y(O))" acty="-0.01834593463776646" shape="dcross" is3D="true" x3D="x3D(P2)/d3D(O,P2)" actx3D="0.0" y3D="y3D(P2)/d3D(O,P2)" acty3D="1.0" z3D="z3D(P2)/d3D(O,P2)" actz3D="-0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="11" hidden="super" showname="true" x="x(O)+(y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3))*(x(X)-x(O))+(z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3))*(x(Y)-x(O))+(x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3))*(x(Z)-x(O))" actx="1.1420722105637728" y="y(O)+(y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3))*(y(X)-y(O))+(z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3))*(y(Y)-y(O))+(x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3))*(y(Z)-y(O))" acty="0.6098799813936578" shape="dcross" is3D="true" x3D="y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3)" actx3D="-1.0" y3D="z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3)" acty3D="0.0" z3D="x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="12" hidden="super" showname="true" x="x(O)+(x3D(A)+x3D(P3)*E1)*(x(X)-x(O))+(y3D(A)+y3D(P3)*E1)*(x(Y)-x(O))+(z3D(A)+z3D(P3)*E1)*(x(Z)-x(O))" actx="-0.8315429407473958" y="y(O)+(x3D(A)+x3D(P3)*E1)*(y(X)-y(O))+(y3D(A)+y3D(P3)*E1)*(y(Y)-y(O))+(z3D(A)+z3D(P3)*E1)*(y(Z)-y(O))" acty="0.9596294032555133" shape="dcross" is3D="true" x3D="x3D(A)+x3D(P3)*E1" actx3D="1.2784877518064197" y3D="y3D(A)+y3D(P3)*E1" acty3D="-0.5943286976171858" z3D="z3D(A)+z3D(P3)*E1" actz3D="1.028176000540179" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="13" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-0.7020146895779398" y="y(O)+(x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.479230828251095" shape="dcross" is3D="true" x3D="x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1" actx3D="0.3274312355112662" y3D="y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1" acty3D="-1.2853117032422383" z3D="z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1" actz3D="1.028176000540179" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="14" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-1.9827959674569313" y="y(O)+(x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1)*(y(Z)-y(O))" acty="0.8538466125562119" shape="dcross" is3D="true" x3D="x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1" actx3D="2.2295442681015736" y3D="y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1" acty3D="-1.2853117032422385" z3D="z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1" actz3D="1.028176000540179" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="15" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-2.564781216445158" y="y(O)+(x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.3080706774748092" shape="dcross" is3D="true" x3D="x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1" actx3D="1.8662730040988929" y3D="y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1" acty3D="-2.403345691992133" z3D="z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1" actz3D="1.028176000540179" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="16" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-1.773214854561418" y="y(O)+(x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.6945793788019428" shape="dcross" is3D="true" x3D="x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1" actx3D="0.6907024995139465" y3D="y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1" acty3D="-2.403345691992133" z3D="z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1" actz3D="1.028176000540179" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="17" type="thin" point1="P5" point2="P6" point3="P9" point4="P8" point5="P7">Conique passant par P5, P6, P9, P8, P7</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dspherepoint" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="O" n="0" x="0.46872564496308566" y="0.2810960421746347">Point</Point>
<Point name="X" n="1" x="-0.2046209206376014" y="-0.04768789704438847">Point</Point>
<Point name="Y" n="2" x="1.2080526379734584" y="-0.01834593463776646">Point</Point>
<Point name="Z" n="3" x="0.46872564496308566" y="1.1767721129917037">Point</Point>
<Point name="A" n="4" x="-1.4729985834964605" y="1.1122643546759525">Point</Point>
<Point name="B" n="5" x="-0.4453494057527294" y="1.9360148860419601">Point</Point>
<Circle name="c1" n="215" color="5" target="true" fixed="d3D(A,B)" midpoint="A" filled="true" acute="true">Sphère de centre A de rayon d3D(A,B)</Circle>
<Point name="D" n="6" hidden="super" showname="true" x="x(O)+(x3D(A))*(x(X)-x(O))+(y3D(A))*(x(Y)-x(O))+(z3D(A)+1)*(x(Z)-x(O))" actx="-1.4729985834964605" y="y(O)+(x3D(A))*(y(X)-y(O))+(y3D(A))*(y(Y)-y(O))+(z3D(A)+1)*(y(Z)-y(O))" acty="2.0079404254930213" shape="dcross" is3D="true" x3D="x3D(A)" actx3D="2.2876636091466542" y3D="y3D(A)" acty3D="-0.5428366579134807" z3D="z3D(A)+1" actz3D="2.586251246275493" fixed="true" fixed3D="true">Point</Point>
<Expression name="E1" n="7" type="thick" hidden="super" showname="true" showvalue="true" x="2.6620659650437917" y="2.3356562329422994" value="d3D(A,B)" prompt="Valeur">Expression &quot;d3D(A,B)&quot; à 2.66207, 2.33566</Expression>
<Point name="P10" n="8" hidden="super" showname="true" x="x(O)+(x3D(C)-x3D(A))*(x(X)-x(O))+(y3D(C)-y3D(A))*(x(Y)-x(O))+(z3D(C)-z3D(A))*(x(Z)-x(O))" actx="0.46872564496308566" y="y(O)+(x3D(C)-x3D(A))*(y(X)-y(O))+(y3D(C)-y3D(A))*(y(Y)-y(O))+(z3D(C)-z3D(A))*(y(Z)-y(O))" acty="1.1767721129917035" shape="dcross" is3D="true" x3D="x3D(D)-x3D(A)" actx3D="0.0" y3D="y3D(D)-y3D(A)" acty3D="0.0" z3D="z3D(D)-z3D(A)" actz3D="0.9999999999999998" fixed="true" fixed3D="true">Point</Point>
<Point name="P1" n="9" hidden="super" showname="true" x="x(O)+(x3D(P12)/d3D(O,P12))*(x(X)-x(O))+(y3D(P12)/d3D(O,P12))*(x(Y)-x(O))+(z3D(P12)/d3D(O,P12))*(x(Z)-x(O))" actx="0.46872564496308566" y="y(O)+(x3D(P12)/d3D(O,P12))*(y(X)-y(O))+(y3D(P12)/d3D(O,P12))*(y(Y)-y(O))+(z3D(P12)/d3D(O,P12))*(y(Z)-y(O))" acty="1.1767721129917037" shape="dcross" is3D="true" x3D="x3D(P10)/d3D(O,P10)" actx3D="0.0" y3D="y3D(P10)/d3D(O,P10)" acty3D="0.0" z3D="z3D(P10)/d3D(O,P10)" actz3D="1.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P2" n="10" hidden="super" showname="true" x="x(O)+(if(x3D(P1)==0,0,-y3D(P1)/x3D(P1)))*(x(X)-x(O))+(if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1))*(x(Y)-x(O))+(if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0))*(x(Z)-x(O))" actx="1.2080526379734584" y="y(O)+(if(x3D(P1)==0,0,-y3D(P1)/x3D(P1)))*(y(X)-y(O))+(if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1))*(y(Y)-y(O))+(if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0))*(y(Z)-y(O))" acty="-0.01834593463776646" shape="dcross" is3D="true" x3D="if(x3D(P1)==0,0,-y3D(P1)/x3D(P1))" actx3D="0.0" y3D="if(x3D(P1)==0,if(y3D(P1)==0,1,-z3D(P1)/y3D(P1)),1)" acty3D="1.0" z3D="if(x3D(P1)==0,if(y3D(P1)==0,-y3D(P1)/z3D(P1),1),0)" actz3D="-0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="11" hidden="super" showname="true" x="x(O)+(x3D(P2)/d3D(O,P2))*(x(X)-x(O))+(y3D(P2)/d3D(O,P2))*(x(Y)-x(O))+(z3D(P2)/d3D(O,P2))*(x(Z)-x(O))" actx="1.2080526379734584" y="y(O)+(x3D(P2)/d3D(O,P2))*(y(X)-y(O))+(y3D(P2)/d3D(O,P2))*(y(Y)-y(O))+(z3D(P2)/d3D(O,P2))*(y(Z)-y(O))" acty="-0.01834593463776646" shape="dcross" is3D="true" x3D="x3D(P2)/d3D(O,P2)" actx3D="0.0" y3D="y3D(P2)/d3D(O,P2)" acty3D="1.0" z3D="z3D(P2)/d3D(O,P2)" actz3D="-0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="12" hidden="super" showname="true" x="x(O)+(y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3))*(x(X)-x(O))+(z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3))*(x(Y)-x(O))+(x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3))*(x(Z)-x(O))" actx="1.1420722105637728" y="y(O)+(y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3))*(y(X)-y(O))+(z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3))*(y(Y)-y(O))+(x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3))*(y(Z)-y(O))" acty="0.6098799813936578" shape="dcross" is3D="true" x3D="y3D(P1)*z3D(P3)-z3D(P1)*y3D(P3)" actx3D="-1.0" y3D="z3D(P1)*x3D(P3)-x3D(P1)*z3D(P3)" acty3D="0.0" z3D="x3D(P1)*y3D(P3)-y3D(P1)*x3D(P3)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="13" hidden="super" showname="true" x="x(O)+(x3D(A)+x3D(P3)*E1)*(x(X)-x(O))+(y3D(A)+y3D(P3)*E1)*(x(Y)-x(O))+(z3D(A)+z3D(P3)*E1)*(x(Z)-x(O))" actx="-0.43127464245655034" y="y(O)+(x3D(A)+x3D(P3)*E1)*(y(X)-y(O))+(y3D(A)+y3D(P3)*E1)*(y(Y)-y(O))+(z3D(A)+z3D(P3)*E1)*(y(Z)-y(O))" acty="0.6903456649123563" shape="dcross" is3D="true" x3D="x3D(A)+x3D(P3)*E1" actx3D="2.2876636091466542" y3D="y3D(A)+y3D(P3)*E1" acty3D="0.8661798542501633" z3D="z3D(A)+z3D(P3)*E1" actz3D="1.5862512462754934" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="14" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-0.24876719776710704" y="y(O)+(x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.4224726524748905" shape="dcross" is3D="true" x3D="x3D(A)+(cos(72)*x3D(P3)+sin(72)*x3D(P4))*E1" actx3D="0.9476092736859512" y3D="y3D(A)+(cos(72)*y3D(P3)+sin(72)*y3D(P4))*E1" acty3D="-0.10742661029999984" z3D="z3D(A)+(cos(72)*z3D(P3)+sin(72)*z3D(P4))*E1" actz3D="1.5862512462754934" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="15" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-2.0534091667686583" y="y(O)+(x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1)*(y(Z)-y(O))" acty="0.54129596611429" shape="dcross" is3D="true" x3D="x3D(A)+(cos(288)*x3D(P3)+sin(288)*x3D(P4))*E1" actx3D="3.6277179446073573" y3D="y3D(A)+(cos(288)*y3D(P3)+sin(288)*y3D(P4))*E1" acty3D="-0.10742661030000017" z3D="z3D(A)+(cos(288)*z3D(P3)+sin(288)*z3D(P4))*E1" actz3D="1.5862512462754934" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="16" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-2.87343599242874" y="y(O)+(x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.1813051738066846" shape="dcross" is3D="true" x3D="x3D(A)+(cos(216)*x3D(P3)+sin(216)*x3D(P4))*E1" actx3D="3.115862735233022" y3D="y3D(A)+(cos(216)*y3D(P3)+sin(216)*y3D(P4))*E1" acty3D="-1.6827549616087836" z3D="z3D(A)+(cos(216)*z3D(P3)+sin(216)*z3D(P4))*E1" actz3D="1.5862512462754934" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="17" hidden="super" showname="true" x="x(O)+(x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1)*(x(X)-x(O))+(y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1)*(x(Y)-x(O))+(z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1)*(x(Z)-x(O))" actx="-1.758105918061247" y="y(O)+(x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1)*(y(X)-y(O))+(y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1)*(y(Y)-y(O))+(z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1)*(y(Z)-y(O))" acty="1.7259023160715417" shape="dcross" is3D="true" x3D="x3D(A)+(cos(144)*x3D(P3)+sin(144)*x3D(P4))*E1" actx3D="1.459464483060286" y3D="y3D(A)+(cos(144)*y3D(P3)+sin(144)*y3D(P4))*E1" acty3D="-1.6827549616087834" z3D="z3D(A)+(cos(144)*z3D(P3)+sin(144)*z3D(P4))*E1" actz3D="1.5862512462754934" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="18" type="thin" point1="P5" point2="P6" point3="P9" point4="P8" point5="P7">Conique passant par P5, P6, P9, P8, P7</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dspheredroite" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="c1">c1</Parameter>
<Parameter name="l1">l1</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.7460278040367916" y="0.21584847533376372">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.19399924053546713" y="0.09985788687667019">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.0871277241082344" y="-0.1038065103451592">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.7460278040367916" y="1.1562562983127012">Point</Point>
<Point name="F" n="4" parameter="true" x="-0.45350535160783745" y="1.6994924562438003">Point</Point>
<Circle name="c1" n="5" parameter="true" mainparameter="true" midpoint="F">???</Circle>
<Point name="P1" n="6" parameter="true" x="1.275555169675266" y="1.4629700264456407">Point</Point>
<Point name="P2" n="7" parameter="true" x="3.3381201185245053" y="1.7545287049490557">Point</Point>
<Line name="l1" n="8" color="5" type="thin" parameter="true" mainparameter="true" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Expression name="E1" n="9" type="thick" hidden="super" showname="true" showvalue="true" x="2.515258939651831" y="1.0877965171106263" value="-(sqrt((-y3D(P1)^2+2*y3D(F)*y3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P2)^2+(((2*y3D(P1)-2*y3D(F))*z3D(P1)-2*z3D(F)*y3D(P1)+2*y3D(F)*z3D(F))*y3D(P2)+((2*x3D(P1)-2*x3D(F))*z3D(P1)-2*z3D(F)*x3D(P1)+2*x3D(F)*z3D(F))*x3D(P2)+(-2*y3D(F)*y3D(P1)-2*x3D(F)*x3D(P1)+2*y3D(F)^2+2*x3D(F)^2-2*c1^2)*z3D(P1)+2*z3D(F)*y3D(P1)^2-2*y3D(F)*z3D(F)*y3D(P1)+2*z3D(F)*x3D(P1)^2-2*x3D(F)*z3D(F)*x3D(P1))*z3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P2)^2+(((2*x3D(P1)-2*x3D(F))*y3D(P1)-2*y3D(F)*x3D(P1)+2*x3D(F)*y3D(F))*x3D(P2)+2*y3D(F)*z3D(P1)^2+(-2*z3D(F)*y3D(P1)-2*y3D(F)*z3D(F))*z3D(P1)+(-2*x3D(F)*x3D(P1)+2*z3D(F)^2+2*x3D(F)^2-2*c1^2)*y3D(P1)+2*y3D(F)*x3D(P1)^2-2*x3D(F)*y3D(F)*x3D(P1))*y3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-y3D(P1)^2+2*y3D(F)*y3D(P1)-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P2)^2+(2*x3D(F)*z3D(P1)^2+(-2*z3D(F)*x3D(P1)-2*x3D(F)*z3D(F))*z3D(P1)+2*x3D(F)*y3D(P1)^2+(-2*y3D(F)*x3D(P1)-2*x3D(F)*y3D(F))*y3D(P1)+(2*z3D(F)^2+2*y3D(F)^2-2*c1^2)*x3D(P1))*x3D(P2)+(-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P1)^2+(2*y3D(F)*z3D(F)*y3D(P1)+2*x3D(F)*z3D(F)*x3D(P1))*z3D(P1)+(-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P1)^2+2*x3D(F)*y3D(F)*x3D(P1)*y3D(P1)+(-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P1)^2)+(z3D(P1)-z3D(F))*z3D(P2)+(y3D(P1)-y3D(F))*y3D(P2)+(x3D(P1)-x3D(F))*x3D(P2)-z3D(P1)^2+z3D(F)*z3D(P1)-y3D(P1)^2+y3D(F)*y3D(P1)-x3D(P1)^2+x3D(F)*x3D(P1))/(z3D(P2)^2-2*z3D(P1)*z3D(P2)+y3D(P2)^2-2*y3D(P1)*y3D(P2)+x3D(P2)^2-2*x3D(P1)*x3D(P2)+z3D(P1)^2+y3D(P1)^2+x3D(P1)^2)" prompt="Valeur">Expression &quot;-(sqrt((-y3D(P1)^2+2*y3D(F)*y3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P2)^2+(((2*y3D(P1)-2*y3D(F))*z3D(P1)-2*z3D(F)*y3D(P1)+2*y3D(F)*z3D(F))*y3D(P2)+((2*x3D(P1)-2*x3D(F))*z3D(P1)-2*z3D(F)*x3D(P1)+2*x3D(F)*z3D(F))*x3D(P2)+(-2*y3D(F)*y3D(P1)-2*x3D(F)*x3D(P1)+2*y3D(F)^2+2*x3D(F)^2-2*c1^2)*z3D(P1)+2*z3D(F)*y3D(P1)^2-2*y3D(F)*z3D(F)*y3D(P1)+2*z3D(F)*x3D(P1)^2-2*x3D(F)*z3D(F)*x3D(P1))*z3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P2)^2+(((2*x3D(P1)-2*x3D(F))*y3D(P1)-2*y3D(F)*x3D(P1)+2*x3D(F)*y3D(F))*x3D(P2)+2*y3D(F)*z3D(P1)^2+(-2*z3D(F)*y3D(P1)-2*y3D(F)*z3D(F))*z3D(P1)+(-2*x3D(F)*x3D(P1)+2*z3D(F)^2+2*x3D(F)^2-2*c1^2)*y3D(P1)+2*y3D(F)*x3D(P1)^2-2*x3D(F)*y3D(F)*x3D(P1))*y3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-y3D(P1)^2+2*y3D(F)*y3D(P1)-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P2)^2+(2*x3D(F)*z3D(P1)^2+(-2*z3D(F)*x3D(P1)-2*x3D(F)*z3D(F))*z3D(P1)+2*x3D(F)*y3D(P1)^2+(-2*y3D(F)*x3D(P1)-2*x3D(F)*y3D(F))*y3D(P1)+(2*z3D(F)^2+2*y3D(F)^2-2*c1^2)*x3D(P1))*x3D(P2)+(-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P1)^2+(2*y3D(F)*z3D(F)*y3D(P1)+2*x3D(F)*z3D(F)*x3D(P1))*z3D(P1)+(-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P1)^2+2*x3D(F)*y3D(F)*x3D(P1)*y3D(P1)+(-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P1)^2)+(z3D(P1)-z3D(F))*z3D(P2)+(y3D(P1)-y3D(F))*y3D(P2)+(x3D(P1)-x3D(F))*x3D(P2)-z3D(P1)^2+z3D(F)*z3D(P1)-y3D(P1)^2+y3D(F)*y3D(P1)-x3D(P1)^2+x3D(F)*x3D(P1))/(z3D(P2)^2-2*z3D(P1)*z3D(P2)+y3D(P2)^2-2*y3D(P1)*y3D(P2)+x3D(P2)^2-2*x3D(P1)*x3D(P2)+z3D(P1)^2+y3D(P1)^2+x3D(P1)^2)&quot; à 2.51526, 1.0878</Expression>
<Expression name="E2" n="10" type="thick" hidden="super" showname="true" showvalue="true" x="2.9149002865521716" y="0.402697065281473" value="(sqrt((-y3D(P1)^2+2*y3D(F)*y3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P2)^2+(((2*y3D(P1)-2*y3D(F))*z3D(P1)-2*z3D(F)*y3D(P1)+2*y3D(F)*z3D(F))*y3D(P2)+((2*x3D(P1)-2*x3D(F))*z3D(P1)-2*z3D(F)*x3D(P1)+2*x3D(F)*z3D(F))*x3D(P2)+(-2*y3D(F)*y3D(P1)-2*x3D(F)*x3D(P1)+2*y3D(F)^2+2*x3D(F)^2-2*c1^2)*z3D(P1)+2*z3D(F)*y3D(P1)^2-2*y3D(F)*z3D(F)*y3D(P1)+2*z3D(F)*x3D(P1)^2-2*x3D(F)*z3D(F)*x3D(P1))*z3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P2)^2+(((2*x3D(P1)-2*x3D(F))*y3D(P1)-2*y3D(F)*x3D(P1)+2*x3D(F)*y3D(F))*x3D(P2)+2*y3D(F)*z3D(P1)^2+(-2*z3D(F)*y3D(P1)-2*y3D(F)*z3D(F))*z3D(P1)+(-2*x3D(F)*x3D(P1)+2*z3D(F)^2+2*x3D(F)^2-2*c1^2)*y3D(P1)+2*y3D(F)*x3D(P1)^2-2*x3D(F)*y3D(F)*x3D(P1))*y3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-y3D(P1)^2+2*y3D(F)*y3D(P1)-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P2)^2+(2*x3D(F)*z3D(P1)^2+(-2*z3D(F)*x3D(P1)-2*x3D(F)*z3D(F))*z3D(P1)+2*x3D(F)*y3D(P1)^2+(-2*y3D(F)*x3D(P1)-2*x3D(F)*y3D(F))*y3D(P1)+(2*z3D(F)^2+2*y3D(F)^2-2*c1^2)*x3D(P1))*x3D(P2)+(-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P1)^2+(2*y3D(F)*z3D(F)*y3D(P1)+2*x3D(F)*z3D(F)*x3D(P1))*z3D(P1)+(-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P1)^2+2*x3D(F)*y3D(F)*x3D(P1)*y3D(P1)+(-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P1)^2)+(z3D(F)-z3D(P1))*z3D(P2)+(y3D(F)-y3D(P1))*y3D(P2)+(x3D(F)-x3D(P1))*x3D(P2)+z3D(P1)^2-z3D(F)*z3D(P1)+y3D(P1)^2-y3D(F)*y3D(P1)+x3D(P1)^2-x3D(F)*x3D(P1))/(z3D(P2)^2-2*z3D(P1)*z3D(P2)+y3D(P2)^2-2*y3D(P1)*y3D(P2)+x3D(P2)^2-2*x3D(P1)*x3D(P2)+z3D(P1)^2+y3D(P1)^2+x3D(P1)^2)" prompt="Valeur">Expression &quot;(sqrt((-y3D(P1)^2+2*y3D(F)*y3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P2)^2+(((2*y3D(P1)-2*y3D(F))*z3D(P1)-2*z3D(F)*y3D(P1)+2*y3D(F)*z3D(F))*y3D(P2)+((2*x3D(P1)-2*x3D(F))*z3D(P1)-2*z3D(F)*x3D(P1)+2*x3D(F)*z3D(F))*x3D(P2)+(-2*y3D(F)*y3D(P1)-2*x3D(F)*x3D(P1)+2*y3D(F)^2+2*x3D(F)^2-2*c1^2)*z3D(P1)+2*z3D(F)*y3D(P1)^2-2*y3D(F)*z3D(F)*y3D(P1)+2*z3D(F)*x3D(P1)^2-2*x3D(F)*z3D(F)*x3D(P1))*z3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-x3D(P1)^2+2*x3D(F)*x3D(P1)-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P2)^2+(((2*x3D(P1)-2*x3D(F))*y3D(P1)-2*y3D(F)*x3D(P1)+2*x3D(F)*y3D(F))*x3D(P2)+2*y3D(F)*z3D(P1)^2+(-2*z3D(F)*y3D(P1)-2*y3D(F)*z3D(F))*z3D(P1)+(-2*x3D(F)*x3D(P1)+2*z3D(F)^2+2*x3D(F)^2-2*c1^2)*y3D(P1)+2*y3D(F)*x3D(P1)^2-2*x3D(F)*y3D(F)*x3D(P1))*y3D(P2)+(-z3D(P1)^2+2*z3D(F)*z3D(P1)-y3D(P1)^2+2*y3D(F)*y3D(P1)-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P2)^2+(2*x3D(F)*z3D(P1)^2+(-2*z3D(F)*x3D(P1)-2*x3D(F)*z3D(F))*z3D(P1)+2*x3D(F)*y3D(P1)^2+(-2*y3D(F)*x3D(P1)-2*x3D(F)*y3D(F))*y3D(P1)+(2*z3D(F)^2+2*y3D(F)^2-2*c1^2)*x3D(P1))*x3D(P2)+(-y3D(F)^2-x3D(F)^2+c1^2)*z3D(P1)^2+(2*y3D(F)*z3D(F)*y3D(P1)+2*x3D(F)*z3D(F)*x3D(P1))*z3D(P1)+(-z3D(F)^2-x3D(F)^2+c1^2)*y3D(P1)^2+2*x3D(F)*y3D(F)*x3D(P1)*y3D(P1)+(-z3D(F)^2-y3D(F)^2+c1^2)*x3D(P1)^2)+(z3D(F)-z3D(P1))*z3D(P2)+(y3D(F)-y3D(P1))*y3D(P2)+(x3D(F)-x3D(P1))*x3D(P2)+z3D(P1)^2-z3D(F)*z3D(P1)+y3D(P1)^2-y3D(F)*y3D(P1)+x3D(P1)^2-x3D(F)*x3D(P1))/(z3D(P2)^2-2*z3D(P1)*z3D(P2)+y3D(P2)^2-2*y3D(P1)*y3D(P2)+x3D(P2)^2-2*x3D(P1)*x3D(P2)+z3D(P1)^2+y3D(P1)^2+x3D(P1)^2)&quot; à 2.9149, 0.4027</Expression>
<Point name="P3" n="11" showname="true" target="true" x="x(O)+(x3D(B)+a1*(x3D(C)-x3D(B)))*(x(X)-x(O))+(y3D(B)+a1*(y3D(C)-y3D(B)))*(x(Y)-x(O))+(z3D(B)+a1*(z3D(C)-z3D(B)))*(x(Z)-x(O))" actx="-0.13527783771906235" y="y(O)+(x3D(B)+a1*(x3D(C)-x3D(B)))*(y(X)-y(O))+(y3D(B)+a1*(y3D(C)-y3D(B)))*(y(Y)-y(O))+(z3D(B)+a1*(z3D(C)-z3D(B)))*(y(Z)-y(O))" acty="1.2635384364467959" shape="dcross" is3D="true" x3D="x3D(P1)+E1*(x3D(P2)-x3D(P1))" actx3D="1.125278942600954" y3D="y3D(P1)+E1*(y3D(P2)-y3D(P1))" acty3D="0.5174055653245309" z3D="z3D(P1)+E1*(z3D(P2)-z3D(P1))" actz3D="1.428745022730201" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="12" showname="true" target="true" x="x(O)+(x3D(B)+a2*(x3D(C)-x3D(B)))*(x(X)-x(O))+(y3D(B)+a2*(y3D(C)-y3D(B)))*(x(Y)-x(O))+(z3D(B)+a2*(z3D(C)-z3D(B)))*(x(Z)-x(O))" actx="0.40535216584576117" y="y(O)+(x3D(B)+a2*(x3D(C)-x3D(B)))*(y(X)-y(O))+(y3D(B)+a2*(y3D(C)-y3D(B)))*(y(Y)-y(O))+(z3D(B)+a2*(z3D(C)-z3D(B)))*(y(Z)-y(O))" acty="1.3399604514173309" shape="dcross" is3D="true" x3D="x3D(P1)+E2*(x3D(P2)-x3D(P1))" actx3D="0.455199417777027" y3D="y3D(P1)+E2*(y3D(P2)-y3D(P1))" acty3D="0.25571429384871225" z3D="z3D(P1)+E2*(z3D(P2)-z3D(P1))" actz3D="1.338409935136351" fixed="true" fixed3D="true">Point</Point>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dsphereplan" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="c1">c1</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Parameter name="D">D</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.6807802371959184" y="0.4849946885523573">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="-0.21439807015162315" y="0.16011688069598362">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.1264884355212246" y="-0.1675029931694112">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.6807802371959184" y="1.1696122352966363">Point</Point>
<Point name="F" n="4" parameter="true" x="-1.3446429559139106" y="1.6860507832548906">Point</Point>
<Circle name="c1" n="5" parameter="true" mainparameter="true" midpoint="F">???</Circle>
<Point name="B" n="6" parameter="true" mainparameter="true" x="-1.3412448778136747" y="2.8192324367742194">Point</Point>
<Point name="C" n="7" parameter="true" mainparameter="true" x="-0.5993498749448103" y="2.4567390513927134">Point</Point>
<Point name="D" n="8" parameter="true" mainparameter="true" x="0.4142759712395959" y="1.360800525805656">Point</Point>
<Point name="P23" n="9" hidden="super" showname="true" x="x(O)+((y3D(C)-y3D(B))*(z3D(D)-z3D(B))-(z3D(C)-z3D(B))*(y3D(D)-y3D(B)))*(x(X)-x(O))+((z3D(C)-z3D(B))*(x3D(D)-x3D(B))-(x3D(C)-x3D(B))*(z3D(D)-z3D(B)))*(x(Y)-x(O))+((x3D(C)-x3D(B))*(y3D(D)-y3D(B))-(y3D(C)-y3D(B))*(x3D(D)-x3D(B)))*(x(Z)-x(O))" actx="-1.0225640200118609" y="y(O)+((y3D(C)-y3D(B))*(z3D(D)-z3D(B))-(z3D(C)-z3D(B))*(y3D(D)-y3D(B)))*(y(X)-y(O))+((z3D(C)-z3D(B))*(x3D(D)-x3D(B))-(x3D(C)-x3D(B))*(z3D(D)-z3D(B)))*(y(Y)-y(O))+((x3D(C)-x3D(B))*(y3D(D)-y3D(B))-(y3D(C)-y3D(B))*(x3D(D)-x3D(B)))*(y(Z)-y(O))" acty="-0.8721302014928238" shape="dcross" is3D="true" x3D="(y3D(C)-y3D(B))*(z3D(D)-z3D(B))-(z3D(C)-z3D(B))*(y3D(D)-y3D(B))" actx3D="1.8297145647710777" y3D="(z3D(C)-z3D(B))*(x3D(D)-x3D(B))-(x3D(C)-x3D(B))*(z3D(D)-z3D(B))" acty3D="-0.14678543143851241" z3D="(x3D(C)-x3D(B))*(y3D(D)-y3D(B))-(y3D(C)-y3D(B))*(x3D(D)-x3D(B))" actz3D="-1.2539386275535995" fixed="true" fixed3D="true">Point</Point>
<Expression name="E1" n="10" type="thick" hidden="super" showname="true" showvalue="true" x="1.9121943346181238" y="1.1279647562538542" value="(x3D(P23)*(x3D(F)-x3D(B))+y3D(P23)*(y3D(F)-y3D(B))+z3D(P23)*(z3D(F)-z3D(B)))/(x3D(P23)*x3D(P23)+y3D(P23)*y3D(P23)+z3D(P23)*z3D(P23))" prompt="Valeur">Expression &quot;(x3D(P23)*(x3D(F)-x3D(B))+y3D(P23)*(y3D(F)-y3D(B))+z3D(P23)*(z3D(F)-z3D(B)))/(x3D(P23)*x3D(P23)+y3D(P23)*y3D(P23)+z3D(P23)*z3D(P23))&quot; à 1.91219, 1.12796</Expression>
<Point name="P1" n="11" showname="true" target="true" x="x(O)+(x3D(A)-E2*x3D(P12))*(x(X)-x(O))+(y3D(A)-E2*y3D(P12))*(x(Y)-x(O))+(z3D(A)-E2*z3D(P12))*(x(Z)-x(O))" actx="-0.8110101643672748" y="y(O)+(x3D(A)-E2*x3D(P12))*(y(X)-y(O))+(y3D(A)-E2*y3D(P12))*(y(Y)-y(O))+(z3D(A)-E2*z3D(P12))*(y(Z)-y(O))" acty="2.1112181212652303" shape="dcross" is3D="true" x3D="x3D(F)-E1*x3D(P23)" actx3D="1.2142374709064936" y3D="y3D(F)-E1*y3D(P23)" acty3D="-0.9082878869185326" z3D="z3D(F)-E1*z3D(P23)" actz3D="2.0859040302412013" fixed="true" fixed3D="true">Point</Point>
<Expression name="E2" n="12" type="thick" hidden="super" showname="true" showvalue="true" x="2.515258939651829" y="2.3030324495218615" value="sqrt(c1*c1-d3D(F,P1)*d3D(F,P1))" prompt="Valeur">Expression &quot;sqrt(c1*c1-d3D(F,P1)*d3D(F,P1))&quot; à 2.51526, 2.30303</Expression>
<Point name="P2" n="13" hidden="super" showname="true" x="x(O)+(x3D(E)-x3D(A))*(x(X)-x(O))+(y3D(E)-y3D(A))*(x(Y)-x(O))+(z3D(E)-z3D(A))*(x(Z)-x(O))" actx="1.214413028742554" y="y(O)+(x3D(E)-x3D(A))*(y(X)-y(O))+(y3D(E)-y3D(A))*(y(Y)-y(O))+(z3D(E)-z3D(A))*(y(Z)-y(O))" acty="0.9101620265626971" shape="dcross" is3D="true" x3D="x3D(P1)-x3D(F)" actx3D="-0.5732227568212738" y3D="y3D(P1)-y3D(F)" acty3D="0.04598572438040971" z3D="z3D(P1)-z3D(F)" actz3D="0.3928405942709914" fixed="true" fixed3D="true">Point</Point>
<Point name="P3" n="14" hidden="super" showname="true" x="x(O)+(x3D(P13)/d3D(O,P13))*(x(X)-x(O))+(y3D(P13)/d3D(O,P13))*(x(Y)-x(O))+(z3D(P13)/d3D(O,P13))*(x(Z)-x(O))" actx="1.4470143005962017" y="y(O)+(x3D(P13)/d3D(O,P13))*(y(X)-y(O))+(y3D(P13)/d3D(O,P13))*(y(Y)-y(O))+(z3D(P13)/d3D(O,P13))*(y(Z)-y(O))" acty="1.0954850897972623" shape="dcross" is3D="true" x3D="x3D(P2)/d3D(O,P2)" actx3D="-0.823080607396092" y3D="y3D(P2)/d3D(O,P2)" acty3D="0.06603010348798523" z3D="z3D(P2)/d3D(O,P2)" actz3D="0.5640729909877321" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="15" hidden="super" showname="true" x="x(O)+(if(x3D(P14)==0,0,-y3D(P14)/x3D(P14)))*(x(X)-x(O))+(if(x3D(P14)==0,if(y3D(P14)==0,1,-z3D(P14)/y3D(P14)),1))*(x(Y)-x(O))+(if(x3D(P14)==0,if(y3D(P14)==0,-y3D(P14)/z3D(P14),1),0))*(x(Z)-x(O))" actx="1.0546744287967136" y="y(O)+(if(x3D(P14)==0,0,-y3D(P14)/x3D(P14)))*(y(X)-y(O))+(if(x3D(P14)==0,if(y3D(P14)==0,1,-z3D(P14)/y3D(P14)),1))*(y(Y)-y(O))+(if(x3D(P14)==0,if(y3D(P14)==0,-y3D(P14)/z3D(P14),1),0))*(y(Z)-y(O))" acty="-0.1935657081464668" shape="dcross" is3D="true" x3D="if(x3D(P3)==0,0,-y3D(P3)/x3D(P3))" actx3D="0.08022313111820102" y3D="if(x3D(P3)==0,if(y3D(P3)==0,1,-z3D(P3)/y3D(P3)),1)" acty3D="1.0" z3D="if(x3D(P3)==0,if(y3D(P3)==0,-y3D(P3)/z3D(P3),1),0)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="16" hidden="super" showname="true" x="x(O)+(x3D(P15)/d3D(O,P15))*(x(X)-x(O))+(y3D(P15)/d3D(O,P15))*(x(Y)-x(O))+(z3D(P15)/d3D(O,P15))*(x(Z)-x(O))" actx="1.0534770602660473" y="y(O)+(x3D(P15)/d3D(O,P15))*(y(X)-y(O))+(y3D(P15)/d3D(O,P15))*(y(Y)-y(O))+(z3D(P15)/d3D(O,P15))*(y(Z)-y(O))" acty="-0.19139266859833948" shape="dcross" is3D="true" x3D="x3D(P4)/d3D(O,P4)" actx3D="0.07996622246652822" y3D="y3D(P4)/d3D(O,P4)" acty3D="0.9967975738656439" z3D="z3D(P4)/d3D(O,P4)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="17" hidden="super" showname="true" x="x(O)+(y3D(P14)*z3D(P16)-z3D(P14)*y3D(P16))*(x(X)-x(O))+(z3D(P14)*x3D(P16)-x3D(P14)*z3D(P16))*(x(Y)-x(O))+(x3D(P14)*y3D(P16)-y3D(P14)*x3D(P16))*(x(Z)-x(O))" actx="1.204213554972427" y="y(O)+(y3D(P14)*z3D(P16)-z3D(P14)*y3D(P16))*(y(X)-y(O))+(z3D(P14)*x3D(P16)-x3D(P14)*z3D(P16))*(y(Y)-y(O))+(x3D(P14)*y3D(P16)-y3D(P14)*x3D(P16))*(y(Z)-y(O))" acty="0.07292477570439049" shape="dcross" is3D="true" x3D="y3D(P3)*z3D(P5)-z3D(P3)*y3D(P5)" actx3D="-0.5622665888997086" y3D="z3D(P3)*x3D(P5)-x3D(P3)*z3D(P5)" acty3D="0.04510678628468495" z3D="x3D(P3)*y3D(P5)-y3D(P3)*x3D(P5)" actz3D="-0.8257249304932932" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="18" hidden="super" showname="true" x="x(O)+(x3D(E)+(cos(72)*x3D(P16)+sin(72)*x3D(P17))*E3)*(x(X)-x(O))+(y3D(E)+(cos(72)*y3D(P16)+sin(72)*y3D(P17))*E3)*(x(Y)-x(O))+(z3D(E)+(cos(72)*z3D(P16)+sin(72)*z3D(P17))*E3)*(x(Z)-x(O))" actx="-0.3711208747308897" y="y(O)+(x3D(E)+(cos(72)*x3D(P16)+sin(72)*x3D(P17))*E3)*(y(X)-y(O))+(y3D(E)+(cos(72)*y3D(P16)+sin(72)*y3D(P17))*E3)*(y(Y)-y(O))+(z3D(E)+(cos(72)*z3D(P16)+sin(72)*z3D(P17))*E3)*(y(Z)-y(O))" acty="1.679988597353803" shape="dcross" is3D="true" x3D="x3D(P1)+(cos(72)*x3D(P5)+sin(72)*x3D(P6))*E2" actx3D="0.8482255937290085" y3D="y3D(P1)+(cos(72)*y3D(P5)+sin(72)*y3D(P6))*E2" acty3D="-0.6564563133076173" z3D="z3D(P1)+(cos(72)*z3D(P5)+sin(72)*z3D(P6))*E2" actz3D="1.5223497595574402" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="19" hidden="super" showname="true" x="x(O)+(x3D(E)+(cos(288)*x3D(P16)+sin(288)*x3D(P17))*E3)*(x(X)-x(O))+(y3D(E)+(cos(288)*y3D(P16)+sin(288)*y3D(P17))*E3)*(x(Y)-x(O))+(z3D(E)+(cos(288)*z3D(P16)+sin(288)*z3D(P17))*E3)*(x(Z)-x(O))" actx="-1.0856035571779294" y="y(O)+(x3D(E)+(cos(288)*x3D(P16)+sin(288)*x3D(P17))*E3)*(y(X)-y(O))+(y3D(E)+(cos(288)*y3D(P16)+sin(288)*y3D(P17))*E3)*(y(Y)-y(O))+(z3D(E)+(cos(288)*z3D(P16)+sin(288)*z3D(P17))*E3)*(y(Z)-y(O))" acty="2.2424610397615306" shape="dcross" is3D="true" x3D="x3D(P1)+(cos(288)*x3D(P5)+sin(288)*x3D(P6))*E2" actx3D="1.6157154096121809" y3D="y3D(P1)+(cos(288)*y3D(P5)+sin(288)*y3D(P6))*E2" acty3D="-0.7180267494390972" z3D="z3D(P1)+(cos(288)*z3D(P5)+sin(288)*z3D(P6))*E2" actz3D="2.6494583009249624" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="20" hidden="super" showname="true" x="x(O)+(x3D(E)+(cos(216)*x3D(P16)+sin(216)*x3D(P17))*E3)*(x(X)-x(O))+(y3D(E)+(cos(216)*y3D(P16)+sin(216)*y3D(P17))*E3)*(x(Y)-x(O))+(z3D(E)+(cos(216)*z3D(P16)+sin(216)*z3D(P17))*E3)*(x(Z)-x(O))" actx="-1.248172593475338" y="y(O)+(x3D(E)+(cos(216)*x3D(P16)+sin(216)*x3D(P17))*E3)*(y(X)-y(O))+(y3D(E)+(cos(216)*y3D(P16)+sin(216)*y3D(P17))*E3)*(y(Y)-y(O))+(z3D(E)+(cos(216)*z3D(P16)+sin(216)*z3D(P17))*E3)*(y(Z)-y(O))" acty="2.6777192294100547" shape="dcross" is3D="true" x3D="x3D(P1)+(cos(216)*x3D(P5)+sin(216)*x3D(P6))*E2" actx3D="1.404979189760129" y3D="y3D(P1)+(cos(216)*y3D(P5)+sin(216)*y3D(P6))*E2" acty3D="-1.5060210699408" z3D="z3D(P1)+(cos(216)*z3D(P5)+sin(216)*z3D(P6))*E2" actz3D="2.434199724028924" fixed="true" fixed3D="true">Point</Point>
<Point name="P10" n="21" hidden="super" showname="true" x="x(O)+(x3D(E)+(cos(144)*x3D(P16)+sin(144)*x3D(P17))*E3)*(x(X)-x(O))+(y3D(E)+(cos(144)*y3D(P16)+sin(144)*y3D(P17))*E3)*(x(Y)-x(O))+(z3D(E)+(cos(144)*z3D(P16)+sin(144)*z3D(P17))*E3)*(x(Z)-x(O))" actx="-0.8065980113498699" y="y(O)+(x3D(E)+(cos(144)*x3D(P16)+sin(144)*x3D(P17))*E3)*(y(X)-y(O))+(y3D(E)+(cos(144)*y3D(P16)+sin(144)*y3D(P17))*E3)*(y(Y)-y(O))+(z3D(E)+(cos(144)*z3D(P16)+sin(144)*z3D(P17))*E3)*(y(Z)-y(O))" acty="2.3300921422669116" shape="dcross" is3D="true" x3D="x3D(P1)+(cos(144)*x3D(P5)+sin(144)*x3D(P6))*E2" actx3D="0.9306443975249297" y3D="y3D(P1)+(cos(144)*y3D(P5)+sin(144)*y3D(P6))*E2" acty3D="-1.467968447709391" z3D="z3D(P1)+(cos(144)*z3D(P5)+sin(144)*z3D(P6))*E2" actz3D="1.7376083364534785" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="22" hidden="super" showname="true" x="x(O)+(x3D(E)+x3D(P16)*E3)*(x(X)-x(O))+(y3D(E)+y3D(P16)*E3)*(x(Y)-x(O))+(z3D(E)+z3D(P16)*E3)*(x(Z)-x(O))" actx="-0.5435557851023469" y="y(O)+(x3D(E)+x3D(P16)*E3)*(y(X)-y(O))+(y3D(E)+y3D(P16)*E3)*(y(Y)-y(O))+(z3D(E)+z3D(P16)*E3)*(y(Z)-y(O))" acty="1.6258295975338513" shape="dcross" is3D="true" x3D="x3D(P1)+x3D(P5)*E2" actx3D="1.2716227639062199" y3D="y3D(P1)+y3D(P5)*E2" acty3D="-0.19296685419575732" z3D="z3D(P1)+z3D(P5)*E2" actz3D="2.0859040302412013" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="23" color="5" point1="P11" point2="P7" point3="P10" point4="P9" point5="P8">Conique passant par P11, P7, P10, P9, P8</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dspheresphere" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="c1">c1</Parameter>
<Parameter name="c2">c2</Parameter>
<Objects>
<Point name="O" n="0" parameter="true" mainparameter="true" x="0.7291067973829604" y="-0.6983556251582417">Point</Point>
<Point name="X" n="1" parameter="true" mainparameter="true" x="0.58870823142588" y="-1.021748580098214">Point</Point>
<Point name="Y" n="2" parameter="true" mainparameter="true" x="1.71920186487759" y="-0.7442137539428708">Point</Point>
<Point name="Z" n="3" parameter="true" mainparameter="true" x="0.7291067973829604" y="0.24679728771859288">Point</Point>
<Point name="E" n="4" parameter="true" x="0.1369010968251122" y="0.06655959558839686">Point</Point>
<Point name="P1" n="5" parameter="true" x="-1.292376199009124" y="0.16575810596311502">Point</Point>
<Circle name="c1" n="6" parameter="true" mainparameter="true" midpoint="E">???</Circle>
<Circle name="c2" n="7" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Midpoint name="P2" n="8" hidden="super" showname="true" first="E" second="P1" shape="dcross">Milieu de E et P1</Midpoint>
<Expression name="E1" n="9" type="thick" hidden="super" showname="true" showvalue="true" x="2.914900286552168" y="1.7402721855193433" value="((c1*c1-c2*c2)/2+(x3D(P2)-x3D(E))*(x3D(P1)-x3D(E))+(y3D(P2)-y3D(E))*(y3D(P1)-y3D(E))+(z3D(P2)-z3D(E))*(z3D(P1)-z3D(E)))/d3D(E,P1)/d3D(E,P1)" prompt="Valeur">Expression &quot;((c1*c1-c2*c2)/2+(x3D(P2)-x3D(E))*(x3D(P1)-x3D(E))+(y3D(P2)-y3D(E))*(y3D(P1)-y3D(E))+(z3D(P2)-z3D(E))*(z3D(P1)-z3D(E)))/d3D(E,P1)/d3D(E,P1)&quot; à 2.9149, 1.74027</Expression>
<Point name="P3" n="10" hidden="super" showname="true" x="x(O)+(x3D(A)+E3*(x3D(B)-x3D(A)))*(x(X)-x(O))+(y3D(A)+E3*(y3D(B)-y3D(A)))*(x(Y)-x(O))+(z3D(A)+E3*(z3D(B)-z3D(A)))*(x(Z)-x(O))" actx="-0.3387478108842593" y="y(O)+(x3D(A)+E3*(x3D(B)-x3D(A)))*(y(X)-y(O))+(y3D(A)+E3*(y3D(B)-y3D(A)))*(y(Y)-y(O))+(z3D(A)+E3*(z3D(B)-z3D(A)))*(y(Z)-y(O))" acty="0.09957184817482823" shape="dcross" is3D="true" x3D="x3D(E)+E1*(x3D(P1)-x3D(E))" actx3D="1.0722259395058629" y3D="y3D(E)+E1*(y3D(P1)-y3D(E))" acty3D="-0.9264924693543016" z3D="z3D(E)+E1*(z3D(P1)-z3D(E))" actz3D="1.1661505374201282" fixed="true" fixed3D="true">Point</Point>
<Expression name="E2" n="11" type="thick" hidden="super" showname="true" showvalue="true" x="2.8659646114215143" y="1.1938238132270422" value="sqrt(c1*c1-d3D(E,P3)*d3D(E,P3))" prompt="Valeur">Expression &quot;sqrt(c1*c1-d3D(E,P3)*d3D(E,P3))&quot; à 2.86596, 1.19382</Expression>
<Point name="P33" n="12" hidden="super" showname="true" x="x(O)+(x3D(B)-x3D(A))*(x(X)-x(O))+(y3D(B)-y3D(A))*(x(Y)-x(O))+(z3D(B)-z3D(A))*(x(Z)-x(O))" actx="-0.7001704984512758" y="y(O)+(x3D(B)-x3D(A))*(y(X)-y(O))+(y3D(B)-y3D(A))*(y(Y)-y(O))+(z3D(B)-z3D(A))*(y(Z)-y(O))" acty="-0.5991571147835234" shape="dcross" is3D="true" x3D="x3D(P1)-x3D(E)" actx3D="-1.343840560297885" y3D="y3D(P1)-y3D(E)" acty3D="-1.634136596063584" z3D="z3D(P1)-z3D(E)" actz3D="-0.4341398098358642" fixed="true" fixed3D="true">Point</Point>
<Point name="P4" n="13" hidden="super" showname="true" x="x(O)+(x3D(P23)/d3D(O,P23))*(x(X)-x(O))+(y3D(P23)/d3D(O,P23))*(x(Y)-x(O))+(z3D(P23)/d3D(O,P23))*(x(Z)-x(O))" actx="0.06734670294922851" y="y(O)+(x3D(P23)/d3D(O,P23))*(y(X)-y(O))+(y3D(P23)/d3D(O,P23))*(y(Y)-y(O))+(z3D(P23)/d3D(O,P23))*(y(Z)-y(O))" acty="-0.6524263882043003" shape="dcross" is3D="true" x3D="x3D(P33)/d3D(O,P33)" actx3D="-0.6222026045460574" y3D="y3D(P33)/d3D(O,P33)" acty3D="-0.7566106250204322" z3D="z3D(P33)/d3D(O,P33)" actz3D="-0.20100816153154916" fixed="true" fixed3D="true">Point</Point>
<Point name="P5" n="14" hidden="super" showname="true" x="x(O)+(if(x3D(P24)==0,0,-y3D(P24)/x3D(P24)))*(x(X)-x(O))+(if(x3D(P24)==0,if(y3D(P24)==0,1,-z3D(P24)/y3D(P24)),1))*(x(Y)-x(O))+(if(x3D(P24)==0,if(y3D(P24)==0,-y3D(P24)/z3D(P24),1),0))*(x(Z)-x(O))" actx="1.889929287046225" y="y(O)+(if(x3D(P24)==0,0,-y3D(P24)/x3D(P24)))*(y(X)-y(O))+(if(x3D(P24)==0,if(y3D(P24)==0,1,-z3D(P24)/y3D(P24)),1))*(y(Y)-y(O))+(if(x3D(P24)==0,if(y3D(P24)==0,-y3D(P24)/z3D(P24),1),0))*(y(Z)-y(O))" acty="-0.35096154963419407" shape="dcross" is3D="true" x3D="if(x3D(P4)==0,0,-y3D(P4)/x3D(P4))" actx3D="-1.216019700805392" y3D="if(x3D(P4)==0,if(y3D(P4)==0,1,-z3D(P4)/y3D(P4)),1)" acty3D="1.0" z3D="if(x3D(P4)==0,if(y3D(P4)==0,-y3D(P4)/z3D(P4),1),0)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P6" n="15" hidden="super" showname="true" x="x(O)+(x3D(P25)/d3D(O,P25))*(x(X)-x(O))+(y3D(P25)/d3D(O,P25))*(x(Y)-x(O))+(z3D(P25)/d3D(O,P25))*(x(Z)-x(O))" actx="1.4664225070517434" y="y(O)+(x3D(P25)/d3D(O,P25))*(y(X)-y(O))+(y3D(P25)/d3D(O,P25))*(y(Y)-y(O))+(z3D(P25)/d3D(O,P25))*(y(Z)-y(O))" acty="-0.47770250066196684" shape="dcross" is3D="true" x3D="x3D(P5)/d3D(O,P5)" actx3D="-0.7723751362972258" y3D="y3D(P5)/d3D(O,P5)" acty3D="0.6351666307590803" z3D="z3D(P5)/d3D(O,P5)" actz3D="0.0" fixed="true" fixed3D="true">Point</Point>
<Point name="P7" n="16" hidden="super" showname="true" x="x(O)+(y3D(P24)*z3D(P26)-z3D(P24)*y3D(P26))*(x(X)-x(O))+(z3D(P24)*x3D(P26)-x3D(P24)*z3D(P26))*(x(Y)-x(O))+(x3D(P24)*y3D(P26)-y3D(P24)*x3D(P26))*(x(Z)-x(O))" actx="0.8648975249407603" y="y(O)+(y3D(P24)*z3D(P26)-z3D(P24)*y3D(P26))*(y(X)-y(O))+(z3D(P24)*x3D(P26)-x3D(P24)*z3D(P26))*(y(Y)-y(O))+(x3D(P24)*y3D(P26)-y3D(P24)*x3D(P26))*(y(Z)-y(O))" acty="-1.672625969489249" shape="dcross" is3D="true" x3D="y3D(P4)*z3D(P6)-z3D(P4)*y3D(P6)" actx3D="0.12767367671507104" y3D="z3D(P4)*x3D(P6)-x3D(P4)*z3D(P6)" acty3D="0.15525370615978507" z3D="x3D(P4)*y3D(P6)-y3D(P4)*x3D(P6)" actz3D="-0.9795895666031291" fixed="true" fixed3D="true">Point</Point>
<Point name="P8" n="17" hidden="super" showname="true" x="x(O)+(x3D(D)+(cos(72)*x3D(P26)+sin(72)*x3D(P27))*E4)*(x(X)-x(O))+(y3D(D)+(cos(72)*y3D(P26)+sin(72)*y3D(P27))*E4)*(x(Y)-x(O))+(z3D(D)+(cos(72)*z3D(P26)+sin(72)*z3D(P27))*E4)*(x(Z)-x(O))" actx="-0.0905503710170712" y="y(O)+(x3D(D)+(cos(72)*x3D(P26)+sin(72)*x3D(P27))*E4)*(y(X)-y(O))+(y3D(D)+(cos(72)*y3D(P26)+sin(72)*y3D(P27))*E4)*(y(Y)-y(O))+(z3D(D)+(cos(72)*z3D(P26)+sin(72)*z3D(P27))*E4)*(y(Z)-y(O))" acty="-0.4972352841951795" shape="dcross" is3D="true" x3D="x3D(P3)+(cos(72)*x3D(P6)+sin(72)*x3D(P7))*E2" actx3D="0.9907058119491057" y3D="y3D(P3)+(cos(72)*y3D(P6)+sin(72)*y3D(P7))*E2" acty3D="-0.6873718650464075" z3D="z3D(P3)+(cos(72)*z3D(P6)+sin(72)*z3D(P7))*E2" actz3D="0.5184198522584574" fixed="true" fixed3D="true">Point</Point>
<Point name="P9" n="18" hidden="super" showname="true" x="x(O)+(x3D(D)+(cos(288)*x3D(P26)+sin(288)*x3D(P27))*E4)*(x(X)-x(O))+(y3D(D)+(cos(288)*y3D(P26)+sin(288)*y3D(P27))*E4)*(x(Y)-x(O))+(z3D(D)+(cos(288)*z3D(P26)+sin(288)*z3D(P27))*E4)*(x(Z)-x(O))" actx="-0.2701272550474214" y="y(O)+(x3D(D)+(cos(288)*x3D(P26)+sin(288)*x3D(P27))*E4)*(y(X)-y(O))+(y3D(D)+(cos(288)*y3D(P26)+sin(288)*y3D(P27))*E4)*(y(Y)-y(O))+(z3D(D)+(cos(288)*z3D(P26)+sin(288)*z3D(P27))*E4)*(y(Z)-y(O))" acty="0.7911916635407593" shape="dcross" is3D="true" x3D="x3D(P3)+(cos(288)*x3D(P6)+sin(288)*x3D(P7))*E2" actx3D="0.8218633478903457" y3D="y3D(P3)+(cos(288)*y3D(P6)+sin(288)*y3D(P7))*E2" acty3D="-0.8926876276743863" z3D="z3D(P3)+(cos(288)*z3D(P6)+sin(288)*z3D(P7))*E2" actz3D="1.813881222581799" fixed="true" fixed3D="true">Point</Point>
<Point name="P10" n="19" hidden="super" showname="true" x="x(O)+(x3D(D)+(cos(216)*x3D(P26)+sin(216)*x3D(P27))*E4)*(x(X)-x(O))+(y3D(D)+(cos(216)*y3D(P26)+sin(216)*y3D(P27))*E4)*(x(Y)-x(O))+(z3D(D)+(cos(216)*z3D(P26)+sin(216)*z3D(P27))*E4)*(x(Z)-x(O))" actx="-0.8089602603469159" y="y(O)+(x3D(D)+(cos(216)*x3D(P26)+sin(216)*x3D(P27))*E4)*(y(X)-y(O))+(y3D(D)+(cos(216)*y3D(P26)+sin(216)*y3D(P27))*E4)*(y(Y)-y(O))+(z3D(D)+(cos(216)*z3D(P26)+sin(216)*z3D(P27))*E4)*(y(Z)-y(O))" acty="0.3736062577119268" shape="dcross" is3D="true" x3D="x3D(P3)+(cos(216)*x3D(P6)+sin(216)*x3D(P7))*E2" actx3D="1.4544908682754403" y3D="y3D(P3)+(cos(216)*y3D(P6)+sin(216)*y3D(P7))*E2" acty3D="-1.3472025762148045" z3D="z3D(P3)+(cos(216)*z3D(P6)+sin(216)*z3D(P7))*E2" actz3D="1.5664701164062977" fixed="true" fixed3D="true">Point</Point>
<Point name="P11" n="20" hidden="super" showname="true" x="x(O)+(x3D(D)+(cos(144)*x3D(P26)+sin(144)*x3D(P27))*E4)*(x(X)-x(O))+(y3D(D)+(cos(144)*y3D(P26)+sin(144)*y3D(P27))*E4)*(x(Y)-x(O))+(z3D(D)+(cos(144)*z3D(P26)+sin(144)*z3D(P27))*E4)*(x(Z)-x(O))" actx="-0.6979756424223614" y="y(O)+(x3D(D)+(cos(144)*x3D(P26)+sin(144)*x3D(P27))*E4)*(y(X)-y(O))+(y3D(D)+(cos(144)*y3D(P26)+sin(144)*y3D(P27))*E4)*(y(Y)-y(O))+(z3D(D)+(cos(144)*z3D(P26)+sin(144)*z3D(P27))*E4)*(y(Z)-y(O))" acty="-0.42268538801016775" shape="dcross" is3D="true" x3D="x3D(P3)+(cos(144)*x3D(P6)+sin(144)*x3D(P7))*E2" actx3D="1.5588412498080366" y3D="y3D(P3)+(cos(144)*y3D(P6)+sin(144)*y3D(P7))*E2" acty3D="-1.2203104564846083" z3D="z3D(P3)+(cos(144)*z3D(P6)+sin(144)*z3D(P7))*E2" actz3D="0.7658309584339584" fixed="true" fixed3D="true">Point</Point>
<Point name="P12" n="21" hidden="super" showname="true" x="x(O)+(x3D(D)+x3D(P26)*E4)*(x(X)-x(O))+(y3D(D)+y3D(P26)*E4)*(x(Y)-x(O))+(z3D(D)+z3D(P26)*E4)*(x(Z)-x(O))" actx="0.17387447441247317" y="y(O)+(x3D(D)+x3D(P26)*E4)*(y(X)-y(O))+(y3D(D)+y3D(P26)*E4)*(y(Y)-y(O))+(z3D(D)+z3D(P26)*E4)*(y(Z)-y(O))" acty="0.2529819918268018" shape="dcross" is3D="true" x3D="x3D(P3)+x3D(P6)*E2" actx3D="0.5352284196063862" y3D="y3D(P3)+y3D(P6)*E2" acty3D="-0.4848898213513019" z3D="z3D(P3)+z3D(P6)*E2" actz3D="1.1661505374201282" fixed="true" fixed3D="true">Point</Point>
<Quadric name="quad1" n="22" color="5" point1="P12" point2="P8" point3="P11" point4="P10" point5="P9">Conique passant par P12, P8, P11, P10, P9</Quadric>
</Objects>
</Macro>
<Macro Name="@builtin@/med" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="B" n="0" parameter="true" mainparameter="true" x="3.9216109938781707" y="-13.620312471643809">Point à 3.92161, -13.62031 </Point>
<Point name="A" n="1" parameter="true" mainparameter="true" x="-4.980460890948463" y="17.297848688565512">Point à -4.98046, 17.29785 </Point>
<Circle name="c1" n="2" color="2" hidden="super" bold="true" through="B" midpoint="A" acute="true">Cercle de centre P81 passant par I64</Circle>
<Circle name="c2" n="3" color="2" hidden="super" bold="true" through="A" midpoint="B" acute="true">Cercle de centre I64 passant par P81</Circle>
<Intersection name="I1" n="4" color="2" hidden="super" bold="true" first="c2" second="c1" shape="circle" which="second">Intersection entre c2 et c1</Intersection>
<Intersection name="I2" n="5" color="2" hidden="super" bold="true" first="c1" second="c2" shape="circle" which="second">Intersection entre c1 et c2</Intersection>
<Line name="l1" n="6" color="2" target="true" bold="true" from="I1" to="I2">Droite passant par I1 et I2</Line>
</Objects>
</Macro>
<Macro Name="@builtin@/biss" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="A" n="0" mainparameter="true" x="-0.3037974683544302" y="2.2582278481012654">Point à -0.3038, 2.25823</Point>
<Point name="B" n="1" mainparameter="true" x="-2.470886075949367" y="0.6177215189873415">Point à -2.47089, 0.61772</Point>
<Point name="C" n="2" mainparameter="true" x="3.281012658227848" y="0.2126582278481015">Point à 3.28101, 0.21266</Point>
<Angle name="a1" n="3" color="1" hidden="super" showvalue="true" unit="°" first="C" root="B" filled="true" fixed="angle180(a(C,B,A))/2">Angle C - B de mesure angle180(a(C,B,A))/2</Angle>
<Circle name="c1" n="4" color="4" hidden="super" through="C" midpoint="B" acute="true">Cercle de centre B passant par C</Circle>
<Intersection name="E" n="5" hidden="super" showname="true" first="a1" second="c1" shape="circle" which="first">Intersection entre a1 et c1</Intersection>
<Ray name="r1" n="6" color="3" target="true" from="B" to="E">Demi-droite d&apos;origine B vers E</Ray>
</Objects>
</Macro>
<Macro Name="@builtin@/circ" showduplicates="true">
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Objects>
<Point name="A" n="0" parameter="true" mainparameter="true" x="-5.896980461811722" y="0.7246891651865007">Point à -5.89698, 0.72469 </Point>
<Point name="B" n="1" parameter="true" mainparameter="true" x="-3.7087033747779783" y="2.5435168738898746">Point à -3.7087, 2.54352 </Point>
<Point name="C" n="2" parameter="true" mainparameter="true" x="-0.12788632326820618" y="-0.4973357015985789">Point à -0.12789, -0.49734 </Point>
<Point name="P4" n="3" color="3" target="true" bold="true" x="(x(A)^2*y(C)-x(A)^2*y(B)+y(C)^2*y(B)-y(C)^2*y(A)-y(C)*y(B)^2+y(C)*y(A)^2-y(C)*x(B)^2+y(B)^2*y(A)-y(B)*y(A)^2+y(B)*x(C)^2+y(A)*x(B)^2-y(A)*x(C)^2)/(2*x(A)*y(C)+(-(2*x(A)))*y(B)+(-(2*y(C)))*x(B)+2*y(B)*x(C)+2*y(A)*x(B)+(-(2*y(A)))*x(C))" actx="-3.1193971099479443" y="(-x(A)^2*x(C)+x(A)^2*x(B)+x(A)*x(C)^2-x(A)*x(B)^2+x(A)*y(C)^2-x(A)*y(B)^2-x(C)^2*x(B)+x(C)*x(B)^2+x(C)*y(B)^2-x(C)*y(A)^2-x(B)*y(C)^2+x(B)*y(A)^2)/(2*x(A)*y(C)+(-(2*x(A)))*y(B)+2*x(C)*y(B)+(-(2*x(C)))*y(A)+(-(2*x(B)))*y(C)+2*x(B)*y(A))" acty="-0.3912915155041797" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Circle name="c1" n="4" color="3" target="true" large="true" through="A" midpoint="P4">Cercle de centre P10 passant par P7 </Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/arc" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="N">N</Parameter>
<Parameter name="P">P</Parameter>
<Objects>
<Point name="M" n="0" parameter="true" mainparameter="true" x="-6.74" y="2.9000000000000012">Point à -6.74, 2.9 </Point>
<Point name="N" n="1" parameter="true" mainparameter="true" x="-5.507754235296662" y="4.133357046501844">Point à -5.50775, 4.13336 </Point>
<Point name="P" n="2" parameter="true" mainparameter="true" x="-4.560000000000001" y="3.86">Point à -4.56, 3.86 </Point>
<Point name="P4" n="3" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P,M,N)&lt;180,x(M),x(P))" actx="-6.74" y="if(a(P,M,N)&lt;180,y(M),y(P))" acty="2.9000000000000012" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P5" n="4" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P,M,N)&lt;180,x(P),x(M))" actx="-4.560000000000001" y="if(a(P,M,N)&lt;180,y(P),y(M))" acty="3.86" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Point name="P6" n="5" hidden="super" x="(x(N)^2*y(P4)-x(N)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(N)-y(P4)*y(P5)^2+y(P4)*y(N)^2-y(P4)*x(P5)^2+y(P5)^2*y(N)-y(P5)*y(N)^2+y(P5)*x(P4)^2+y(N)*x(P5)^2-y(N)*x(P4)^2)/(2*x(N)*y(P4)+(-(2*x(N)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(N)*x(P5)+(-(2*y(N)))*x(P4))" actx="-5.385187132991038" y="(-x(N)^2*x(P4)+x(N)^2*x(P5)+x(N)*x(P4)^2-x(N)*x(P5)^2+x(N)*y(P4)^2-x(N)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(N)^2-x(P5)*y(P4)^2+x(P5)*y(N)^2)/(2*x(N)*y(P4)+(-(2*x(N)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(N)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(N))" acty="2.778654114500473" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c1" n="6" target="true" through="P4" midpoint="P6" start="P5" end="P4">Cercle de centre P6 passant par P4</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/t_align" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="a">a</Parameter>
<Parameter name="b">b</Parameter>
<Parameter name="P4">P4</Parameter>
<Objects>
<Point name="a" n="0" mainparameter="true" x="-1.0" y="2.0">Point à -1.0, 2.0</Point>
<Point name="b" n="1" mainparameter="true" x="2.0" y="2.0">Point à 2.0, 2.0</Point>
<Line name="l1" n="2" color="2" hidden="super" from="a" to="b">Droite passant par a et b</Line>
<Point name="M" n="3" mainparameter="true" x="4.0" y="2.0">Point à 4.0, 2.0</Point>
<Plumb name="perp1" n="4" color="2" hidden="super" point="M" line="l1" valid="true">Perpendiculaire passant par M à l1</Plumb>
<Intersection name="I1" n="5" color="2" hidden="super" showname="true" first="perp1" second="l1" shape="circle">Intersection entre perp1 et l1</Intersection>
<Point name="P4" n="6" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P4)-windowcx)+windowcx+d(windowcx)" actx="-3.9869281045751634" y="(windoww/(windoww-d(windoww)))*(y(P4)-windowcy)+windowcy+d(windowcy)" acty="-2.0784313725490193" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P4)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P4)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="7" color="2" target="true" ctag0="green" cexpr0="1" x="if(d(M,I1)~=0,x(P4)+15/pixel,invalid)" y="y(P4)+13/pixel" fixed="true">Les points sont alignés</Text>
<Text name="Text2" n="8" color="2" target="true" ctag0="red" cexpr0="1" x="if(!(d(M,I1)~=0),x(P4)+15/pixel,invalid)" y="y(P4)+13/pixel" fixed="true">Les points ne sont pas alignés</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_para" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="l2">l2</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Line name="l1" n="0" mainparameter="true">???</Line>
<Line name="l2" n="1" mainparameter="true">???</Line>
<Point name="P5" n="2" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-1.5" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="-2.5" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" showname="true" showvalue="true" target="true" ctag0="red" cexpr0="1" x="if(x(l1)*y(l2)~=y(l1)*x(l2),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les objets ne sont pas parallèles</Text>
<Text name="Text2" n="4" showname="true" showvalue="true" target="true" ctag0="green" cexpr0="1" x="if(x(l1)*y(l2)~=y(l1)*x(l2),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les objets sont parallèles</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_perp" showduplicates="true">
<Parameter name="l1">l1</Parameter>
<Parameter name="l2">l2</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Line name="l1" n="0">???</Line>
<Line name="l2" n="1">???</Line>
<Point name="P5" n="2" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-6.0" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="4.0" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" showname="true" showvalue="true" target="true" ctag0="red" cexpr0="1" x="if(x(l2)*x(l1)~=-y(l2)*y(l1),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les objets ne sont pas perpendiculaires</Text>
<Text name="Text2" n="4" showname="true" showvalue="true" target="true" ctag0="green" cexpr0="1" x="if(x(l2)*x(l1)~=-y(l2)*y(l1),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les objets sont perpendiculaires</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_equi" showduplicates="true">
<Parameter name="o">o</Parameter>
<Parameter name="a">a</Parameter>
<Parameter name="b">b</Parameter>
<Parameter name="P5">P5</Parameter>
<Objects>
<Point name="o" n="0" mainparameter="true" x="1.0" y="-1.0">Point à 1.0, -1.0</Point>
<Point name="a" n="1" mainparameter="true" x="1.0" y="3.0">Point à 1.0, 3.0</Point>
<Point name="b" n="2" mainparameter="true" x="5.0" y="0.0">Point à 5.0, 0.0</Point>
<Point name="P5" n="3" color="1" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)" actx="-4.321167883211679" y="(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)" acty="-3.635036496350365" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P5)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P5)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="4" target="true" ctag0="red" cexpr0="1" x="if(d(a,o)~=d(b,o),invalid,x(P5)+15/pixel)" y="y(P5)+13/pixel" fixed="true">Les points ne sont pas équidistants</Text>
<Text name="Text2" n="5" target="true" ctag0="green" cexpr0="1" x="if(d(a,o)~=d(b,o),x(P5)+15/pixel,invalid)" y="y(P5)+13/pixel" fixed="true">Les points sont équidistants</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_app" showduplicates="true">
<Parameter name="P13">P13</Parameter>
<Parameter name="l4">l4</Parameter>
<Parameter name="P14">P14</Parameter>
<Objects>
<Line name="l4" n="0" mainparameter="true">???</Line>
<Point name="P13" n="1" mainparameter="true" x="5.0" y="0.0">Point à 5.0, 0.0</Point>
<Point name="P14" n="2" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P14)-windowcx)+windowcx+d(windowcx)" actx="3.0065359477124183" y="(windoww/(windoww-d(windoww)))*(y(P14)-windowcy)+windowcy+d(windowcy)" acty="-4.1568627450980395" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P14)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P14)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Plumb name="perp1" n="3" color="2" hidden="super" point="P13" line="l4" valid="true">Perpendiculaire passant par P13 à l4</Plumb>
<Intersection name="I1" n="4" color="2" hidden="super" showname="true" first="l4" second="perp1" shape="circle">Intersection entre l4 et perp1</Intersection>
<Point name="P3" n="5" color="5" hidden="super" showname="true" x="if(I1,x(I1),x(P13)+1)" actx="6.0" y="if(I1,y(I1),y(P13))" acty="0.0" shape="circle" fixed="true">Point à &quot;if(I1,x(I1),x(P13)+1)&quot;, &quot;if(I1,y(I1),y(P13))&quot;</Point>
<Text name="Text1" n="6" color="2" target="true" ctag0="green" cexpr0="1" x="if(d(P13,P3)~=0,x(P14)+15/pixel,invalid)" y="y(P14)+13/pixel" fixed="true">Le point est sur l&apos;objet</Text>
<Text name="Text2" n="7" color="2" target="true" ctag0="red" cexpr0="1" x="if(!(d(P13,P3)~=0),x(P14)+15/pixel,invalid)" y="y(P14)+13/pixel" fixed="true">Le point n&apos;est pas sur l&apos;objet</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/t_conf" showduplicates="true">
<Parameter name="M">M</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="P3">P3</Parameter>
<Objects>
<Point name="A" n="0" mainparameter="true" x="3.0" y="2.0">Point à 3.0, 2.0</Point>
<Point name="M" n="1" mainparameter="true" x="3.0" y="2.0">Point à 3.0, 2.0</Point>
<Point name="P3" n="2" color="2" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(P3)-windowcx)+windowcx+d(windowcx)" actx="-4.052287581699346" y="(windoww/(windoww-d(windoww)))*(y(P3)-windowcy)+windowcy+d(windowcy)" acty="-1.7908496732026142" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(P3)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(P3)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Text name="Text1" n="3" target="true" ctag0="green" cexpr0="1" x="if(d(M,A)~=0,x(P3)+15/pixel,invalid)" y="y(P3)+13/pixel" fixed="true">Les points sont confondus</Text>
<Text name="Text2" n="4" target="true" ctag0="red" cexpr0="1" x="if(!(d(M,A)~=0),x(P3)+15/pixel,invalid)" y="y(P3)+13/pixel" fixed="true">Les points ne sont pas confondus</Text>
</Objects>
</Macro>
<Macro Name="@builtin@/function_u" showduplicates="true">
<Parameter name="A">A</Parameter>
<Objects>
<Point name="A" n="0" color="2" hidden="true" mainparameter="true" target="true" ctag0="showname" cexpr0="0" ctag1="hidden" cexpr1="1" x="(windoww/(windoww-d(windoww)))*(x(A)-windowcx)+windowcx+d(windowcx)" actx="0.0607594936708864" y="(windoww/(windoww-d(windoww)))*(y(A)-windowcy)+windowcy+d(windowcy)" acty="0.8810126582278475" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(A)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(A)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Function name="f1" n="1" type="thick" target="true" ctag0="showname" cexpr0="1" ctag1="showvalue" cexpr1="1" f="0" x="x(A)" y="y(A)" fixed="true" var="x">f1(x)=0</Function>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_line" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="P2" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="-0.41196328606916843" y="-1.0802592834702631">Point</Point>
<Point name="B" n="2" mainparameter="true" x="1.0711045437798372" y="-0.21971375257022352">Point</Point>
<Circle name="Hz" n="3" mainparameter="true" midpoint="P2">???</Circle>
<Point name="P1" n="4" color="2" scolor="51,,102,,255" hidden="super" showname="true" x="(y(B)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" actx="3.5038570319321503" y="(x(A)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" acty="-6.120565212684549" shape="circle" fixed="true">Point</Point>
<Point name="P3" n="5" color="5" hidden="super" showname="true" x="(Hz^2-d(P1,A)^2+x(P1)^2+y(P1)^2+(y(P1)^2*(d(P1,A)^2-Hz^2-x(P1)^2-y(P1)^2))/(x(P1)^2+y(P1)^2)+y(P1)*sqrt(x(P1)^2*(x(P1)^2+y(P1)^2-(Hz-d(P1,A))^2)*((Hz+d(P1,A))^2-x(P1)^2-y(P1)^2))/(x(P1)^2+y(P1)^2))/(2*x(P1))" actx="-1.7222475983052445" y="(y(P1)*(Hz^2-d(P1,A)^2+x(P1)^2+y(P1)^2)-sqrt(x(P1)^2*(x(P1)^2+y(P1)^2-(Hz-d(P1,A))^2)*((Hz+d(P1,A))^2-x(P1)^2-y(P1)^2)))/(2*(x(P1)^2+y(P1)^2))" acty="-2.456392316005694" shape="dcross" fixed="true">Point</Point>
<Point name="P4" n="6" color="5" hidden="super" showname="true" x="(Hz^2-d(P1,A)^2+x(P1)^2+y(P1)^2+(y(P1)^2*(d(P1,A)^2-Hz^2-x(P1)^2-y(P1)^2))/(x(P1)^2+y(P1)^2)-y(P1)*sqrt(x(P1)^2*(x(P1)^2+y(P1)^2-(Hz-d(P1,A))^2)*((Hz+d(P1,A))^2-x(P1)^2-y(P1)^2))/(x(P1)^2+y(P1)^2))/(2*x(P1))" actx="2.9902721429335126" y="(y(P1)*(Hz^2-d(P1,A)^2+x(P1)^2+y(P1)^2)+sqrt(x(P1)^2*(x(P1)^2+y(P1)^2-(Hz-d(P1,A))^2)*((Hz+d(P1,A))^2-x(P1)^2-y(P1)^2)))/(2*(x(P1)^2+y(P1)^2))" acty="0.2413969991441962" shape="dcross" fixed="true">Point</Point>
<Point name="P5" n="7" color="5" hidden="super" showname="true" x="(y(P4)*(x(P3)^2+y(P3)^2+Hz^2)-y(P3)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" actx="3.503857031932151" y="(x(P3)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P3)^2+y(P3)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" acty="-6.120565212684552" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="8" color="4" target="true" through="P3" midpoint="P5" start="P3" end="P4" acute="true">Cercle de centre P5 passant par P3</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_segment" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">Premier point</Parameter>
<Parameter name="B">Second point</Parameter>
<Objects>
<Point name="P2" n="0" parameter="true" x="0.0" y="-0.04359673024523225">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P2">???</Circle>
<Point name="A" n="2" mainparameter="true" x="1.068119891008175" y="-1.424159854677566">Point</Point>
<Point name="B" n="3" mainparameter="true" x="2.143505903723888" y="0.3633060853769301">Point</Point>
<Point name="P1" n="4" color="5" hidden="super" showname="true" x="(y(B)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" actx="4.1060046276721" y="(x(A)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" acty="-2.034607275240961" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="5" color="2" target="true" through="A" midpoint="P1" start="A" end="B" acute="true">Cercle de centre P1 passant par A</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_ray" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">Origine de la demi droite</Parameter>
<Parameter name="B">Point de la demi droite</Parameter>
<Objects>
<Point name="P7" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="1.3238770685579198" y="-2.855791962174941">Point</Point>
<Point name="B" n="2" mainparameter="true" x="2.8179669030732857" y="-0.7754137115839237">Point</Point>
<Circle name="Hz" n="3" mainparameter="true" midpoint="P7">???</Circle>
<Point name="P1" n="4" color="2" hidden="super" showname="true" x="(y(B)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" actx="4.9539738628473104" y="(x(A)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" acty="-3.886158275838566" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="143" color="2" hidden="super" fixed="d(P1,A)" midpoint="P1" acute="true">Cercle de centre P1 de rayon d(P1,A)</Circle>
<Intersection name="P2" n="5" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c2" closeto="A" shape="circle" which="first">Intersection entre Hz et c2</Intersection>
<Intersection name="P3" n="6" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c2" awayfrom="P2" shape="circle" which="second">Intersection entre Hz et c2</Intersection>
<Point name="P4" n="7" color="5" hidden="super" showname="true" x="if(d(A,P2)&lt;d(B,P2),x(P3),x(P2))" actx="0.0" y="if(d(A,P2)&lt;d(B,P2),y(P3),y(P2))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P5" n="8" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P4,A,B)&lt;180,x(A),x(P4))" actx="0.0" y="if(a(P4,A,B)&lt;180,y(A),y(P4))" acty="0.0" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P6" n="9" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P4,A,B)&lt;180,x(P4),x(A))" actx="1.3238770685579198" y="if(a(P4,A,B)&lt;180,y(P4),y(A))" acty="-2.855791962174941" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Point name="P8" n="10" hidden="super" x="(x(B)^2*y(P5)-x(B)^2*y(P6)+y(P5)^2*y(P6)-y(P5)^2*y(B)-y(P5)*y(P6)^2+y(P5)*y(B)^2-y(P5)*x(P6)^2+y(P6)^2*y(B)-y(P6)*y(B)^2+y(P6)*x(P5)^2+y(B)*x(P6)^2-y(B)*x(P5)^2)/(2*x(B)*y(P5)+(-(2*x(B)))*y(P6)+(-(2*y(P5)))*x(P6)+2*y(P6)*x(P5)+2*y(B)*x(P6)+(-(2*y(B)))*x(P5))" actx="1.1901341998083355" y="(-x(B)^2*x(P5)+x(B)^2*x(P6)+x(B)*x(P5)^2-x(B)*x(P6)^2+x(B)*y(P5)^2-x(B)*y(P6)^2-x(P5)^2*x(P6)+x(P5)*x(P6)^2+x(P5)*y(P6)^2-x(P5)*y(B)^2-x(P6)*y(P5)^2+x(P6)*y(B)^2)/(2*x(B)*y(P5)+(-(2*x(B)))*y(P6)+2*x(P5)*y(P6)+(-(2*x(P5)))*y(B)+(-(2*x(P6)))*y(P5)+2*x(P6)*y(B))" acty="-1.1830370632923952" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c3" n="11" color="3" target="true" through="P5" midpoint="P8" start="P6" end="P5">Cercle de centre P8 passant par P5</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_plumb" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="rAB">La droite</Parameter>
<Parameter name="P">Le point</Parameter>
<Objects>
<Point name="P8" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P8">???</Circle>
<Point name="P1" n="2" parameter="true" x="4.164773227209625" y="-5.623963392504225">Point</Point>
<Circle name="rAB" n="3" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P" n="4" mainparameter="true" x="2.238978894236137" y="1.8541543967892995">Point</Point>
<Point name="P2" n="5" color="1" hidden="super" showname="true" x="((x(P)*(1+Hz^2/(x(P)^2+y(P)^2))/2*x(P)+y(P)*(1+Hz^2/(x(P)^2+y(P)^2))/2*y(P))*(y(P1)-y(P))-y(P)*((x(P)+x(P1)+(x(P)-x(P1))*rAB^2/((x(P1)-x(P))^2+(y(P1)-y(P))^2))/2*(x(P1)-x(P))+(y(P)+y(P1)+(y(P)-y(P1))*rAB^2/((x(P1)-x(P))^2+(y(P1)-y(P))^2))/2*(y(P1)-y(P))))/(x(P)*(y(P1)-y(P))-y(P)*(x(P1)-x(P)))" actx="6.455952245431247" y="(x(P)*((x(P)+x(P1)+(x(P)-x(P1))*rAB^2/((x(P1)-x(P))^2+(y(P1)-y(P))^2))/2*(x(P1)-x(P))+(y(P)+y(P1)+(y(P)-y(P1))*rAB^2/((x(P1)-x(P))^2+(y(P1)-y(P))^2))/2*(y(P1)-y(P)))-(x(P)*(1+Hz^2/(x(P)^2+y(P)^2))/2*x(P)+y(P)*(1+Hz^2/(x(P)^2+y(P)^2))/2*y(P))*(x(P1)-x(P)))/(x(P)*(y(P1)-y(P))-y(P)*(x(P1)-x(P)))" acty="0.6889008717552748" shape="circle" fixed="true">Point</Point>
<Circle name="c3" n="6" color="1" hidden="super" fixed="d(P2,P)" midpoint="P2" acute="true">Cercle de centre P2 de rayon d(P2,P)</Circle>
<Intersection name="P3" n="7" color="1" hidden="super" first="c3" second="Hz" shape="circle" which="second">Intersection entre c3 et Hz</Intersection>
<Intersection name="P4" n="8" color="1" hidden="super" first="c3" second="Hz" shape="circle" which="first">Intersection entre c3 et Hz</Intersection>
<Point name="P5" n="9" color="5" hidden="super" showname="true" x="(y(P4)*(x(P3)^2+y(P3)^2+Hz^2)-y(P3)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" actx="6.455952245431248" y="(x(P3)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P3)^2+y(P3)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" acty="0.6889008717552743" shape="circle" fixed="true">Point</Point>
<Circle name="c4" n="10" color="1" target="true" through="P3" midpoint="P5" start="P3" end="P4" acute="true">Cercle de centre P5 passant par P3</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_med" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="P3" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P3">???</Circle>
<Point name="A" n="2" mainparameter="true" x="0.5594913714804726" y="-0.88646684831971">Point</Point>
<Point name="B" n="3" mainparameter="true" x="2.8077816712808907" y="-0.027527271287068444">Point</Point>
<Point name="P1" n="4" color="2" hidden="super" showname="true" x="(Hz^2*(x(A)-x(B))+x(A)^2*x(B)+x(B)*y(A)^2-x(A)*(x(B)^2+y(B)^2))/(x(A)^2-x(B)^2+y(A)^2-y(B)^2)" actx="5.489932314747894" y="(-x(B)^2*y(A)+Hz^2*(y(A)-y(B))+y(B)*(x(A)^2+y(A)^2-y(A)*y(B)))/(x(A)^2-x(B)^2+y(A)^2-y(B)^2)" acty="0.9971648422900283" shape="dcross" fixed="true">Point</Point>
<Circle name="c2" n="5" scolor="48,,240,,0" hidden="super" fixed="sqrt(d(P1,P3)^2-Hz^2)" midpoint="P1" acute="true">Cercle de centre P1 de rayon sqrt(d(P1,P3)^2-Hz^2)</Circle>
<Intersection name="P2" n="6" color="2" hidden="super" first="Hz" second="c2" shape="dcross" which="first">Intersection entre Hz et c2</Intersection>
<Intersection name="P4" n="7" color="2" hidden="super" first="c2" second="Hz" shape="dcross" which="first">Intersection entre c2 et Hz</Intersection>
<Point name="P5" n="8" color="5" hidden="super" showname="true" x="(y(P4)*(x(P2)^2+y(P2)^2+Hz^2)-y(P2)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P2)*y(P4)-x(P4)*y(P2)))" actx="5.489932314747893" y="(x(P2)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P2)^2+y(P2)^2+Hz^2))/(2*(x(P2)*y(P4)-x(P4)*y(P2)))" acty="0.9971648422900286" shape="circle" fixed="true">Point</Point>
<Circle name="c3" n="9" color="2" scolor="48,,240,,0" target="true" through="P2" midpoint="P5" start="P2" end="P4" acute="true">Cercle de centre P5 passant par P2</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_biss" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="O">O (sommet)</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="P19" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P19">???</Circle>
<Point name="A" n="2" mainparameter="true" x="1.2699884125144845" y="-0.7415990730011588">Point</Point>
<Point name="O" n="3" mainparameter="true" x="-1.4368482039397454" y="-1.5202780996523755">Point</Point>
<Point name="B" n="4" mainparameter="true" x="-1.4368482039397454" y="0.5191193511008105">Point</Point>
<Point name="P1" n="5" color="2" hidden="super" showname="true" x="(y(A)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" actx="3.076605725344326" y="(x(O)*(x(A)^2+y(A)^2+Hz^2)-x(A)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" acty="-12.115824316667618" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="6" color="2" hidden="super" fixed="d(P1,O)" midpoint="P1" acute="true">Cercle de centre P1 de rayon d(P1,O)</Circle>
<Intersection name="P2" n="7" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c2" closeto="O" shape="circle" which="first">Intersection entre Hz et c2</Intersection>
<Intersection name="P3" n="8" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c2" awayfrom="P2" shape="circle" which="second">Intersection entre Hz et c2</Intersection>
<Point name="P4" n="9" color="5" hidden="super" x="if(d(O,P2)&lt;d(A,P2),x(P3),x(P2))" actx="0.0" y="if(d(O,P2)&lt;d(A,P2),y(P3),y(P2))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P5" n="10" color="2" hidden="super" showname="true" x="(y(B)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(O)*y(B)-x(B)*y(O)))" actx="-9.213072794347678" y="(x(O)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(B)-x(B)*y(O)))" acty="-0.5005793742757821" shape="circle" fixed="true">Point</Point>
<Circle name="c3" n="11" color="2" hidden="super" fixed="d(P5,O)" midpoint="P5" acute="true">Cercle de centre P5 de rayon d(P5,O)</Circle>
<Intersection name="P6" n="12" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c3" closeto="O" shape="circle" which="first">Intersection entre Hz et c3</Intersection>
<Intersection name="P7" n="13" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c3" awayfrom="P6" shape="circle" which="second">Intersection entre Hz et c3</Intersection>
<Point name="P8" n="14" color="5" hidden="super" x="if(d(O,P6)&lt;d(B,P6),x(P7),x(P6))" actx="0.0" y="if(d(O,P6)&lt;d(B,P6),y(P7),y(P6))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P9" n="15" color="5" hidden="super" showname="true" x="(y(P4)*(x(P8)^2+y(P8)^2+Hz^2)-y(P8)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P8)*y(P4)-x(P4)*y(P8)))" actx="6.50426136018179" y="(x(P8)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P8)^2+y(P8)^2+Hz^2))/(2*(x(P8)*y(P4)-x(P4)*y(P8)))" acty="10.462672529039226" shape="circle" fixed="true">Point</Point>
<Circle name="c4" n="16" color="5" hidden="super" through="P8" midpoint="P9" start="P8" end="P4" acute="true">Cercle de centre P9 passant par P8</Circle>
<Point name="P10" n="17" color="1" hidden="super" showname="true" x="((x(O)*(1+Hz^2/(x(O)^2+y(O)^2))/2*x(O)+y(O)*(1+Hz^2/(x(O)^2+y(O)^2))/2*y(O))*(y(P9)-y(O))-y(O)*((x(O)+x(P9)+(x(O)-x(P9))*c4^2/((x(P9)-x(O))^2+(y(P9)-y(O))^2))/2*(x(P9)-x(O))+(y(O)+y(P9)+(y(O)-y(P9))*c4^2/((x(P9)-x(O))^2+(y(P9)-y(O))^2))/2*(y(P9)-y(O))))/(x(O)*(y(P9)-y(O))-y(O)*(x(P9)-x(O)))" actx="-35.44746044924264" y="(x(O)*((x(O)+x(P9)+(x(O)-x(P9))*c4^2/((x(P9)-x(O))^2+(y(P9)-y(O))^2))/2*(x(P9)-x(O))+(y(O)+y(P9)+(y(O)-y(P9))*c4^2/((x(P9)-x(O))^2+(y(P9)-y(O))^2))/2*(y(P9)-y(O)))-(x(O)*(1+Hz^2/(x(O)^2+y(O)^2))/2*x(O)+y(O)*(1+Hz^2/(x(O)^2+y(O)^2))/2*y(O))*(x(P9)-x(O)))/(x(O)*(y(P9)-y(O))-y(O)*(x(P9)-x(O)))" acty="24.29411627516763" shape="circle" fixed="true">Point</Point>
<Circle name="c5" n="18" color="1" hidden="super" fixed="d(P10,O)" midpoint="P10" acute="true">Cercle de centre P10 de rayon d(P10,O)</Circle>
<Intersection name="P11" n="19" color="1" hidden="super" first="c5" second="Hz" shape="circle" which="second">Intersection entre c5 et Hz</Intersection>
<Intersection name="P12" n="20" color="1" hidden="super" first="c5" second="Hz" shape="circle" which="first">Intersection entre c5 et Hz</Intersection>
<Point name="P13" n="21" color="5" hidden="super" showname="true" x="(y(P12)*(x(P11)^2+y(P11)^2+Hz^2)-y(P11)*(x(P12)^2+y(P12)^2+Hz^2))/(2*(x(P11)*y(P12)-x(P12)*y(P11)))" actx="-35.447460449242776" y="(x(P11)*(x(P12)^2+y(P12)^2+Hz^2)-x(P12)*(x(P11)^2+y(P11)^2+Hz^2))/(2*(x(P11)*y(P12)-x(P12)*y(P11)))" acty="24.294116275167724" shape="circle" fixed="true">Point</Point>
<Circle name="c6" n="22" color="5" target="true" through="P11" midpoint="P13" start="P11" end="P12" acute="true">Cercle de centre P13 passant par P11</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_midpoint" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="P2" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="-0.79829086732496" y="-1.6516362772240507">Point</Point>
<Point name="B" n="2" mainparameter="true" x="2.4774544158360783" y="-0.0825818138612">Point</Point>
<Circle name="Hz" n="3" mainparameter="true" midpoint="P2">???</Circle>
<Point name="P1" n="4" color="2" hidden="super" showname="true" x="(Hz^2*(x(A)-x(B))+x(A)^2*x(B)+x(B)*y(A)^2-x(A)*(x(B)^2+y(B)^2))/(x(A)^2-x(B)^2+y(A)^2-y(B)^2)" actx="14.068390885772642" y="(-x(B)^2*y(A)+Hz^2*(y(A)-y(B))+y(B)*(x(A)^2+y(A)^2-y(A)*y(B)))/(x(A)^2-x(B)^2+y(A)^2-y(B)^2)" acty="5.469379352410939" shape="dcross" fixed="true">Point</Point>
<Point name="P3" n="5" color="2" hidden="super" showname="true" x="(y(B)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" actx="4.202153174589746" y="(x(A)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" acty="-7.887214249749084" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="6" color="2" hidden="super" fixed="sqrt(d(P1,P2)^2-Hz^2)" midpoint="P1" acute="true">Cercle de centre P1 de rayon sqrt(d(P1,P2)^2-Hz^2)</Circle>
<Circle name="c3" n="7" color="3" hidden="super" fixed="sqrt(d(P3,P2)^2-Hz^2)" midpoint="P3" acute="true">Cercle de centre P3 de rayon sqrt(d(P3,P2)^2-Hz^2)</Circle>
<Intersection name="P4" n="8" color="2" showname="true" target="true" first="c3" second="c2" closeto="P2" shape="circle" which="second">Intersection entre c3 et c2</Intersection>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_syma" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c33">Axe de la symétrie</Parameter>
<Parameter name="M">M</Parameter>
<Objects>
<Point name="P12" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P12">???</Circle>
<Point name="P1" n="2" hidden="true" parameter="true" x="8.292526416052318" y="4.62767075745222">Point</Point>
<Circle name="c33" n="3" mainparameter="true" midpoint="P1">???</Circle>
<Point name="M" n="4" mainparameter="true" x="-0.6275924256086567" y="3.015329125338143">Point</Point>
<Point name="P2" n="5" color="5" type="thick" target="true" x="x(P1)+c33^2/d(P1,M)^2*(x(M)-x(P1))" actx="1.4997671331193532" y="y(P1)+c33^2/d(P1,M)^2*(y(M)-y(P1))" acty="3.3998565564070224" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_symc" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="O">Le centre</Parameter>
<Parameter name="A">Un point</Parameter>
<Objects>
<Point name="P24" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="1.0419306081642237" y="-1.2068683763084334">Point</Point>
<Circle name="Hz" n="2" mainparameter="true" midpoint="P24">???</Circle>
<Point name="O" n="3" mainparameter="true" x="1.282376133125199" y="0.30593471823769924">Point</Point>
<Point name="P1" n="4" color="2" hidden="super" showname="true" x="(y(O)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(A)*y(O)-x(O)*y(A)))" actx="4.160403248180485" y="(x(A)*(x(O)^2+y(O)^2+Hz^2)-x(O)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(O)-x(O)*y(A)))" acty="-0.927009855928471" shape="circle" fixed="true">Point</Point>
<Point name="P2" n="5" color="2" hidden="super" showname="true" x="(y(P1)*(Hz^2+x(O)^2+y(O)^2)/2-y(O)*((Hz^2+x(O)^2+y(O)^2)/2+x(O)*(x(P1)-x(O))+y(O)*(y(P1)-y(O))))/(x(O)*y(P1)-y(O)*x(P1))" actx="2.9420279601252686" y="(x(O)*((Hz^2+x(O)^2+y(O)^2)/2+x(O)*(x(P1)-x(O))+y(O)*(y(P1)-y(O)))-x(P1)*(Hz^2+x(O)^2+y(O)^2)/2)/(x(O)*y(P1)-y(O)*x(P1))" acty="4.180012320539949" shape="circle" fixed="true">Point</Point>
<Point name="P3" n="6" color="2" target="true" x="x(P2)+d(P2,O)^2/d(P2,A)^2*(x(A)-x(P2))" actx="1.907628148048959" y="y(P2)+d(P2,O)^2/d(P2,A)^2*(y(A)-y(P2))" acty="1.2474318531515047" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_circle" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="O">Centre du cycle</Parameter>
<Parameter name="A">Point du cycle</Parameter>
<Comment>
<P>Si le centre du cercle est sur l&apos;horizon (à l&apos;infini) alors
le cercle devient un horicycle</P>
</Comment>
<Objects>
<Point name="P6" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="O" n="1" mainparameter="true" x="2.871783671552736" y="-1.3290110148932754">Point</Point>
<Point name="A" n="2" mainparameter="true" x="1.4549448281705324" y="-2.7175130814078345">Point</Point>
<Circle name="Hz" n="3" mainparameter="true" midpoint="P6">???</Circle>
<Point name="P1" n="4" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" actx="5.078502785695768" y="y(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" acty="-2.350241840363468" shape="dcross" fixed="true">Point</Point>
<Point name="P2" n="5" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(O)*((x(A)+x(P1)+(x(A)-x(P1))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P1))^2+(y(A)-y(P1))^2))))*(x(P1)-x(A))+(y(A)+y(P1)+(y(A)-y(P1))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P1))^2+(y(A)-y(P1))^2))))*(y(P1)-y(A)))/(2*(x(O)*(x(P1)-x(A))+y(O)*(y(P1)-y(A))))" actx="2.3020332326593724" y="y(O)*((x(A)+x(P1)+(x(A)-x(P1))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P1))^2+(y(A)-y(P1))^2))))*(x(P1)-x(A))+(y(A)+y(P1)+(y(A)-y(P1))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P1))^2+(y(A)-y(P1))^2))))*(y(P1)-y(A)))/(2*(x(O)*(x(P1)-x(A))+y(O)*(y(P1)-y(A))))" acty="-1.0653405244833387" shape="dcross" fixed="true">Point</Point>
<Circle name="c2" n="6" scolor="255,,41,,201" target="true" through="A" midpoint="P2" acute="true">Cercle de centre P2 passant par A</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_angle" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">Premier point</Parameter>
<Parameter name="O">Origine de l&apos;angle</Parameter>
<Parameter name="B">Dernier point</Parameter>
<Comment>
<P>Renvoie la mesure en degré de l&apos;angle AOB</P>
</Comment>
<Objects>
<Point name="P6" n="0" parameter="true" x="0.0" y="0.25531914893617014">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P6">???</Circle>
<Point name="O" n="2" mainparameter="true" x="-1.2671394799054374" y="2.2222222222222223">Point</Point>
<Point name="A" n="3" mainparameter="true" x="-2.7423167848699768" y="0.40661938534278974">Point</Point>
<Point name="B" n="4" mainparameter="true" x="1.4373522458628845" y="1.0118203309692673">Point</Point>
<Point name="P1" n="5" color="2" hidden="super" x="(y(A)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" actx="-4.143583331113412" y="(x(O)*(x(A)^2+y(A)^2+Hz^2)-x(A)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" acty="3.0522406527471415" shape="circle" fixed="true">Point</Point>
<Point name="P2" n="6" color="2" hidden="super" x="(y(B)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(O)*y(B)-x(B)*y(O)))" actx="2.396462905627297" y="(x(O)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(B)-x(B)*y(O)))" acty="6.781458506888652" shape="circle" fixed="true">Point</Point>
<Segment name="s1" n="7" color="3" hidden="super" from="P1" to="O">Segment de P1 à O</Segment>
<Segment name="s2" n="8" color="3" hidden="super" from="P2" to="O">Segment de P2 à O</Segment>
<Plumb name="perp1" n="9" color="1" hidden="super" point="O" line="s1" valid="true">Perpendiculaire passant par O à s1</Plumb>
<Plumb name="perp2" n="10" color="1" hidden="super" point="O" line="s2" valid="true">Perpendiculaire passant par O à s2</Plumb>
<Plumb name="perp3" n="11" color="1" hidden="super" point="A" line="perp1" valid="true">Perpendiculaire passant par A à perp1</Plumb>
<Plumb name="perp4" n="12" color="1" hidden="super" point="B" line="perp2" valid="true">Perpendiculaire passant par B à perp2</Plumb>
<Intersection name="P3" n="13" color="2" hidden="super" first="perp1" second="perp3" shape="circle">Intersection entre perp1 et perp3</Intersection>
<Intersection name="P4" n="14" color="2" hidden="super" first="perp2" second="perp4" shape="circle">Intersection entre perp2 et perp4</Intersection>
<Angle name="a1" n="15" color="4" showvalue="true" xcoffset="0.1844555612982999" ycoffset="-0.4353487161609122" target="true" unit="°" first="P3" root="O" second="P4" display="small" filled="true" acute="true">Angle P3 - O - P4</Angle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_circle3" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">Premier point du rayon</Parameter>
<Parameter name="B">Second point du rayon</Parameter>
<Parameter name="O">Centre </Parameter>
<Objects>
<Point name="P15" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P15">???</Circle>
<Point name="A" n="2" mainparameter="true" x="-2.860998650472335" y="0.4318488529014841">Point</Point>
<Point name="B" n="3" mainparameter="true" x="-1.522267206477733" y="1.8353576248313095">Point</Point>
<Point name="O" n="4" mainparameter="true" x="0.37786774628879805" y="-2.5479082321187585">Point</Point>
<Point name="P1" n="5" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(A)*(Hz^2+x(A)^2+y(A)^2)/(2*(x(A)^2+y(A)^2))" actx="-6.004591869979171" y="y(A)*(Hz^2+x(A)^2+y(A)^2)/(2*(x(A)^2+y(A)^2))" acty="0.9063534898081759" shape="dcross" fixed="true">Point</Point>
<Point name="P2" n="6" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" actx="0.9512414404954936" y="y(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" acty="-6.414085141626773" shape="dcross" fixed="true">Point</Point>
<Point name="P3" n="7" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(A)*((x(O)+x(P1)+(x(O)-x(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(O)-x(P1))^2+(y(O)-y(P1))^2))))*(x(P1)-x(O))+(y(O)+y(P1)+(y(O)-y(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(O)-x(P1))^2+(y(O)-y(P1))^2))))*(y(P1)-y(O)))/(2*(x(A)*(x(P1)-x(O))+y(A)*(y(P1)-y(O))))" actx="-1.458217125253422" y="y(A)*((x(O)+x(P1)+(x(O)-x(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(O)-x(P1))^2+(y(O)-y(P1))^2))))*(x(P1)-x(O))+(y(O)+y(P1)+(y(O)-y(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(O)-x(P1))^2+(y(O)-y(P1))^2))))*(y(P1)-y(O)))/(2*(x(A)*(x(P1)-x(O))+y(A)*(y(P1)-y(O))))" acty="0.22010824532127102" shape="dcross" fixed="true">Point</Point>
<Point name="P4" n="8" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(O)*((x(A)+x(P2)+(x(A)-x(P2))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(x(P2)-x(A))+(y(A)+y(P2)+(y(A)-y(P2))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(y(P2)-y(A)))/(2*(x(O)*(x(P2)-x(A))+y(O)*(y(P2)-y(A))))" actx="0.18407254974559042" y="y(O)*((x(A)+x(P2)+(x(A)-x(P2))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(x(P2)-x(A))+(y(A)+y(P2)+(y(A)-y(P2))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(y(P2)-y(A)))/(2*(x(O)*(x(P2)-x(A))+y(O)*(y(P2)-y(A))))" acty="-1.241174906855984" shape="dcross" fixed="true">Point</Point>
<Circle name="c2" n="9" color="2" hidden="super" through="O" midpoint="P3" acute="true">Cercle de centre P3 passant par O</Circle>
<Circle name="c3" n="10" color="2" hidden="super" through="A" midpoint="P4" acute="true">Cercle de centre P4 passant par A</Circle>
<Intersection name="P5" n="11" color="2" hidden="super" first="c3" second="c2" shape="circle" which="first">Intersection entre c3 et c2</Intersection>
<Intersection name="P6" n="12" color="2" hidden="super" first="c3" second="c2" shape="circle" which="second">Intersection entre c3 et c2</Intersection>
<Point name="P7" n="13" color="2" hidden="super" showname="true" x="(y(P6)*(x(P5)^2+y(P5)^2+Hz^2)-y(P5)*(x(P6)^2+y(P6)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" actx="-37.16181944940876" y="(x(P5)*(x(P6)^2+y(P6)^2+Hz^2)-x(P6)*(x(P5)^2+y(P5)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" acty="31.98860398792299" shape="circle" fixed="true">Point</Point>
<Circle name="c4" n="14" color="5" hidden="super" fixed="d(P7,P5)" midpoint="P7" acute="true">Cercle de centre P7 de rayon d(P7,P5)</Circle>
<Intersection name="P8" n="15" color="1" hidden="super" first="Hz" second="c4" shape="circle" which="first">Intersection entre Hz et c4</Intersection>
<Intersection name="P9" n="16" color="1" hidden="super" first="c4" second="Hz" shape="circle" which="first">Intersection entre c4 et Hz</Intersection>
<Point name="P10" n="17" color="5" hidden="super" showname="true" x="(y(P9)*(x(P8)^2+y(P8)^2+Hz^2)-y(P8)*(x(P9)^2+y(P9)^2+Hz^2))/(2*(x(P8)*y(P9)-x(P9)*y(P8)))" actx="-37.16181944940892" y="(x(P8)*(x(P9)^2+y(P9)^2+Hz^2)-x(P9)*(x(P8)^2+y(P8)^2+Hz^2))/(2*(x(P8)*y(P9)-x(P9)*y(P8)))" acty="31.98860398792311" shape="circle" fixed="true">Point</Point>
<Circle name="c5" n="18" color="2" hidden="super" through="P8" midpoint="P10" start="P8" end="P9" acute="true">Cercle de centre P10 passant par P8</Circle>
<Point name="P11" n="19" color="2" hidden="super" x="x(P10)+c5^2/d(P10,B)^2*(x(B)-x(P10))" actx="2.155113132100901" y="y(P10)+c5^2/d(P10,B)^2*(y(B)-y(P10))" acty="-1.2759316125901456" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P12" n="20" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" actx="0.9512414404954936" y="y(O)*(Hz^2+x(O)^2+y(O)^2)/(2*(x(O)^2+y(O)^2))" acty="-6.414085141626773" shape="dcross" fixed="true">Point</Point>
<Point name="P13" n="21" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(O)*((x(P11)+x(P12)+(x(P11)-x(P12))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(P11)-x(P12))^2+(y(P11)-y(P12))^2))))*(x(P12)-x(P11))+(y(P11)+y(P12)+(y(P11)-y(P12))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(P11)-x(P12))^2+(y(P11)-y(P12))^2))))*(y(P12)-y(P11)))/(2*(x(O)*(x(P12)-x(P11))+y(O)*(y(P12)-y(P11))))" actx="0.3161320180967054" y="y(O)*((x(P11)+x(P12)+(x(P11)-x(P12))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(P11)-x(P12))^2+(y(P11)-y(P12))^2))))*(x(P12)-x(P11))+(y(P11)+y(P12)+(y(P11)-y(P12))*((Hz^2-x(O)^2-y(O)^2)^2/(4*(x(O)^2+y(O)^2)*((x(P11)-x(P12))^2+(y(P11)-y(P12))^2))))*(y(P12)-y(P11)))/(2*(x(O)*(x(P12)-x(P11))+y(O)*(y(P12)-y(P11))))" acty="-2.1316330363092186" shape="dcross" fixed="true">Point</Point>
<Circle name="c6" n="22" color="2" target="true" through="P11" midpoint="P13" acute="true">Cercle de centre P13 passant par P11</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_lineIP" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Objects>
<Point name="P2" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P2">???</Circle>
<Point name="A" n="2" mainparameter="true" x="1.3238770685579198" y="-2.855791962174941">Point</Point>
<Point name="B" n="3" mainparameter="true" x="2.8179669030732857" y="-0.7754137115839237">Point</Point>
<Point name="P1" n="4" color="2" hidden="super" showname="true" x="(y(B)*(x(A)^2+y(A)^2+Hz^2)-y(A)*(x(B)^2+y(B)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" actx="4.9539738628473104" y="(x(A)*(x(B)^2+y(B)^2+Hz^2)-x(B)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(A)*y(B)-x(B)*y(A)))" acty="-3.886158275838566" shape="circle" fixed="true">Point</Point>
<Circle name="c2" n="5" color="2" hidden="super" fixed="d(P1,A)" midpoint="P1" acute="true">Cercle de centre P1 de rayon d(P1,A)</Circle>
<Intersection name="P3" n="6" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c2" closeto="A" shape="circle" which="first">Intersection entre Hz et c2</Intersection>
<Intersection name="P4" n="7" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c2" awayfrom="P3" shape="circle" which="second">Intersection entre Hz et c2</Intersection>
<Point name="P5" n="8" color="5" showname="true" target="true" x="if(d(A,P3)&lt;d(B,P3),x(P3),x(P4))" actx="0.0" y="if(d(A,P3)&lt;d(B,P3),y(P3),y(P4))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P6" n="9" color="5" showname="true" target="true" x="if(d(A,P3)&lt;d(B,P3),x(P4),x(P3))" actx="0.0" y="if(d(A,P3)&lt;d(B,P3),y(P4),y(P3))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P7" n="10" color="5" hidden="super" showname="true" x="(y(P6)*(x(P5)^2+y(P5)^2+Hz^2)-y(P5)*(x(P6)^2+y(P6)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" actx="4.953973862847311" y="(x(P5)*(x(P6)^2+y(P6)^2+Hz^2)-x(P6)*(x(P5)^2+y(P5)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" acty="-3.8861582758385667" shape="circle" fixed="true">Point</Point>
<Circle name="c3" n="11" color="2" target="true" through="P5" midpoint="P7" start="P5" end="P6" acute="true">Cercle de centre P7 passant par P5</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_perp_common" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c3">droite hyperbolique</Parameter>
<Parameter name="c5">droite hyperbolique</Parameter>
<Objects>
<Point name="P17" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" mainparameter="true" midpoint="P17">???</Circle>
<Point name="P1" n="2" parameter="true" x="-5.401563845629293" y="0.9934794901891645">Point</Point>
<Circle name="c3" n="3" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P2" n="4" parameter="true" x="1.9999355683876714" y="-4.86899931441252">Point</Point>
<Circle name="c5" n="5" mainparameter="true" midpoint="P2">???</Circle>
<Line name="l1" n="6" color="4" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Intersection name="P3" n="7" color="5" hidden="super" first="l1" second="Hz" shape="circle" which="first">Intersection entre l1 et Hz</Intersection>
<Intersection name="P4" n="8" color="5" hidden="super" first="l1" second="Hz" shape="circle" which="second">Intersection entre l1 et Hz</Intersection>
<Point name="P5" n="9" color="5" hidden="super" showname="true" x="(y(P3)*(x(P4)^2+y(P4)^2+Hz^2)-y(P4)*(x(P3)^2+y(P3)^2+Hz^2))/(2*(x(P4)*y(P3)-x(P3)*y(P4)))" actx="-5.108868299153805" y="(x(P4)*(x(P3)^2+y(P3)^2+Hz^2)-x(P3)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P4)*y(P3)-x(P3)*y(P4)))" acty="-6.450050734988695" shape="circle" fixed="true">Point</Point>
<Circle name="c6" n="10" color="5" scolor="36,,130,,255" target="true" through="P4" midpoint="P5" start="P4" end="P3" acute="true">Cercle de centre P5 passant par P4</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_distance" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="P2">P2</Parameter>
<Objects>
<Point name="P3" n="0" parameter="true" x="0.0" y="-0.1751824817518246">Point</Point>
<Point name="A" n="1" parameter="true" mainparameter="true" x="-0.5060827250608275" y="-2.472019464720195">Point</Point>
<Point name="B" n="2" parameter="true" mainparameter="true" x="1.9464720194647196" y="-1.343065693430657">Point</Point>
<Point name="P2" n="3" parameter="true" mainparameter="true" x="-0.9537712895377126" y="1.4209245742092458">Point</Point>
<Circle name="Hz" n="4" parameter="true" mainparameter="true" midpoint="P3">???</Circle>
<Expression name="E1" n="5" color="5" type="thick" showname="true" showvalue="true" x="x(P2)+15/pixel" y="y(P2)-5/pixel" value="Argcosh(((Hz^2+x(A)^2+y(A)^2)*(Hz^2+x(B)^2+y(B)^2)-4*Hz^2*(x(A)*x(B)+y(A)*y(B)))/((Hz^2-x(A)^2-y(A)^2)*(Hz^2-x(B)^2-y(B)^2)))" prompt="dist" fixed="true">Expression &quot;Argcosh(((Hz^2+x(A)^2+y(A)^2)*(Hz^2+x(B)^2+y(B)^2)-4*Hz^2*(x(A)*x(B)+y(A)*y(B)))/((Hz^2-x(A)^2-y(A)^2)*(Hz^2-x(B)^2-y(B)^2)))&quot; à -0.80377, 1.37092</Expression>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_horocycle" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Comment>
<P>Les centres des horicycles sont orientés par rapport à A et
B (pour maintenir une continuité des constructions)</P>
</Comment>
<Objects>
<Point name="P11" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="-1.670945157526254" y="-0.43873978996499385">Point</Point>
<Point name="B" n="2" mainparameter="true" x="1.953051643192488" y="-2.572769953051643">Point</Point>
<Circle name="Hz" n="3" mainparameter="true" midpoint="P11">???</Circle>
<Point name="P1" n="4" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(A)*(Hz^2+x(A)^2+y(A)^2)/(2*(x(A)^2+y(A)^2))" actx="-7.844566172405405" y="y(A)*(Hz^2+x(A)^2+y(A)^2)/(2*(x(A)^2+y(A)^2))" acty="-2.0597464251567255" shape="dcross" fixed="true">Point</Point>
<Point name="P2" n="5" color="5" hidden="super" showname="true" xcoffset="0.37870567520159426" ycoffset="0.1952100737355289" keepclose="true" x="x(B)*(Hz^2+x(B)^2+y(B)^2)/(2*(x(B)^2+y(B)^2))" actx="3.3199987823365684" y="y(B)*(Hz^2+x(B)^2+y(B)^2)/(2*(x(B)^2+y(B)^2))" acty="-4.373459934424133" shape="dcross" fixed="true">Point</Point>
<Point name="P3" n="6" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(A)*((x(B)+x(P1)+(x(B)-x(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(B)-x(P1))^2+(y(B)-y(P1))^2))))*(x(P1)-x(B))+(y(B)+y(P1)+(y(B)-y(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(B)-x(P1))^2+(y(B)-y(P1))^2))))*(y(P1)-y(B)))/(2*(x(A)*(x(P1)-x(B))+y(A)*(y(P1)-y(B))))" actx="-0.755722337343591" y="y(A)*((x(B)+x(P1)+(x(B)-x(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(B)-x(P1))^2+(y(B)-y(P1))^2))))*(x(P1)-x(B))+(y(B)+y(P1)+(y(B)-y(P1))*((Hz^2-x(A)^2-y(A)^2)^2/(4*(x(A)^2+y(A)^2)*((x(B)-x(P1))^2+(y(B)-y(P1))^2))))*(y(P1)-y(B)))/(2*(x(A)*(x(P1)-x(B))+y(A)*(y(P1)-y(B))))" acty="-0.1984298874589316" shape="dcross" fixed="true">Point</Point>
<Point name="P4" n="7" color="5" hidden="super" showname="true" xcoffset="0.022346368715083997" ycoffset="0.916201117318435" keepclose="true" x="x(B)*((x(A)+x(P2)+(x(A)-x(P2))*((Hz^2-x(B)^2-y(B)^2)^2/(4*(x(B)^2+y(B)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(x(P2)-x(A))+(y(A)+y(P2)+(y(A)-y(P2))*((Hz^2-x(B)^2-y(B)^2)^2/(4*(x(B)^2+y(B)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(y(P2)-y(A)))/(2*(x(B)*(x(P2)-x(A))+y(B)*(y(P2)-y(A))))" actx="1.0838201317797778" y="y(B)*((x(A)+x(P2)+(x(A)-x(P2))*((Hz^2-x(B)^2-y(B)^2)^2/(4*(x(B)^2+y(B)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(x(P2)-x(A))+(y(A)+y(P2)+(y(A)-y(P2))*((Hz^2-x(B)^2-y(B)^2)^2/(4*(x(B)^2+y(B)^2)*((x(A)-x(P2))^2+(y(A)-y(P2))^2))))*(y(P2)-y(A)))/(2*(x(B)*(x(P2)-x(A))+y(B)*(y(P2)-y(A))))" acty="-1.427724596671438" shape="dcross" fixed="true">Point</Point>
<Circle name="c2" n="8" color="3" hidden="super" through="B" midpoint="P3" acute="true">Cercle de centre P3 passant par B</Circle>
<Circle name="c3" n="9" color="3" hidden="super" through="A" midpoint="P4" acute="true">Cercle de centre P4 passant par A</Circle>
<Intersection name="P5" n="10" color="5" hidden="super" first="c3" second="c2" shape="circle" which="second">Intersection entre c3 et c2</Intersection>
<Intersection name="P6" n="11" color="5" hidden="super" first="c2" second="c3" shape="circle" which="second">Intersection entre c2 et c3</Intersection>
<Point name="P7" n="12" color="2" hidden="super" showname="true" x="(y(P6)*(x(P5)^2+y(P5)^2+Hz^2)-y(P5)*(x(P6)^2+y(P6)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" actx="9.058473071565174" y="(x(P5)*(x(P6)^2+y(P6)^2+Hz^2)-x(P6)*(x(P5)^2+y(P5)^2+Hz^2))/(2*(x(P5)*y(P6)-x(P6)*y(P5)))" acty="-6.75687509535813" shape="circle" fixed="true">Point</Point>
<Circle name="c4" n="13" color="2" hidden="super" fixed="d(P7,P5)" midpoint="P7" acute="true">Cercle de centre P7 de rayon d(P7,P5)</Circle>
<Intersection name="P8" n="14" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c4" closeto="P5" shape="circle" which="first">Intersection entre Hz et c4</Intersection>
<Intersection name="P9" n="15" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c4" awayfrom="P8" shape="circle" which="second">Intersection entre Hz et c4</Intersection>
<Point name="P10" n="16" color="5" scolor="87,,169,,255" showname="true" target="true" x="if(d(P5,P8)&lt;d(P6,P8),x(P8),x(P9))" actx="0.0" y="if(d(P5,P8)&lt;d(P6,P8),y(P8),y(P9))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P12" n="17" color="5" scolor="62,,240,,0" showname="true" xcoffset="0.4712670670859347" ycoffset="0.27693024130935395" keepclose="true" target="true" x="if(d(P5,P8)&lt;d(P6,P8),x(P9),x(P8))" actx="0.0" y="if(d(P5,P8)&lt;d(P6,P8),y(P9),y(P8))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P13" n="18" color="5" hidden="super" x="(x(A)^2*y(P12)-x(A)^2*y(B)+y(P12)^2*y(B)-y(P12)^2*y(A)-y(P12)*y(B)^2+y(P12)*y(A)^2-y(P12)*x(B)^2+y(B)^2*y(A)-y(B)*y(A)^2+y(B)*x(P12)^2+y(A)*x(B)^2-y(A)*x(P12)^2)/(2*x(A)*y(P12)+(-(2*x(A)))*y(B)+(-(2*y(P12)))*x(B)+2*y(B)*x(P12)+2*y(A)*x(B)+(-(2*y(A)))*x(P12))" actx="-0.3007216933456279" y="(-x(A)^2*x(P12)+x(A)^2*x(B)+x(A)*x(P12)^2-x(A)*x(B)^2+x(A)*y(P12)^2-x(A)*y(B)^2-x(P12)^2*x(B)+x(P12)*x(B)^2+x(P12)*y(B)^2-x(P12)*y(A)^2-x(B)*y(P12)^2+x(B)*y(A)^2)/(2*x(A)*y(P12)+(-(2*x(A)))*y(B)+2*x(P12)*y(B)+(-(2*x(P12)))*y(A)+(-(2*x(B)))*y(P12)+2*x(B)*y(A))" acty="-2.2559743309342495" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Circle name="c5" n="19" color="5" scolor="62,,240,,0" target="true" large="true" through="A" midpoint="P13">Cercle de centre P10 passant par P7 </Circle>
<Point name="P14" n="20" color="5" hidden="super" x="(x(A)^2*y(P10)-x(A)^2*y(B)+y(P10)^2*y(B)-y(P10)^2*y(A)-y(P10)*y(B)^2+y(P10)*y(A)^2-y(P10)*x(B)^2+y(B)^2*y(A)-y(B)*y(A)^2+y(B)*x(P10)^2+y(A)*x(B)^2-y(A)*x(P10)^2)/(2*x(A)*y(P10)+(-(2*x(A)))*y(B)+(-(2*y(P10)))*x(B)+2*y(B)*x(P10)+2*y(A)*x(B)+(-(2*y(A)))*x(P10))" actx="-0.3007216933456279" y="(-x(A)^2*x(P10)+x(A)^2*x(B)+x(A)*x(P10)^2-x(A)*x(B)^2+x(A)*y(P10)^2-x(A)*y(B)^2-x(P10)^2*x(B)+x(P10)*x(B)^2+x(P10)*y(B)^2-x(P10)*y(A)^2-x(B)*y(P10)^2+x(B)*y(A)^2)/(2*x(A)*y(P10)+(-(2*x(A)))*y(B)+2*x(P10)*y(B)+(-(2*x(P10)))*y(A)+(-(2*x(B)))*y(P10)+2*x(B)*y(A))" acty="-2.2559743309342495" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Circle name="c6" n="21" color="5" scolor="87,,169,,255" target="true" large="true" through="A" midpoint="P14">Cercle de centre P10 passant par P7 </Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_equidistante" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="rAB">la droite hyperbolique</Parameter>
<Parameter name="M">un point</Parameter>
<Objects>
<Point name="P11" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P11">???</Circle>
<Point name="P1" n="2" parameter="true" x="3.39492105347383" y="-6.651167929515206">Point</Point>
<Circle name="rAB" n="3" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Intersection name="P2" n="4" color="1" hidden="super" first="Hz" second="rAB" shape="circle" which="first">Intersection entre Hz et rAB</Intersection>
<Intersection name="P3" n="5" color="1" hidden="super" first="Hz" second="rAB" shape="circle" which="second">Intersection entre Hz et rAB</Intersection>
<Point name="M" n="6" parameter="true" mainparameter="true" x="-2.305939967335423" y="-0.36314015233628716">Point</Point>
<Point name="P4" n="7" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P3,P2,M)&lt;180,x(P2),x(P3))" actx="0.0" y="if(a(P3,P2,M)&lt;180,y(P2),y(P3))" acty="0.0" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P5" n="8" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P3,P2,M)&lt;180,x(P3),x(P2))" actx="0.0" y="if(a(P3,P2,M)&lt;180,y(P3),y(P2))" acty="0.0" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Point name="P6" n="9" hidden="super" x="(x(M)^2*y(P4)-x(M)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(M)-y(P4)*y(P5)^2+y(P4)*y(M)^2-y(P4)*x(P5)^2+y(P5)^2*y(M)-y(P5)*y(M)^2+y(P5)*x(P4)^2+y(M)*x(P5)^2-y(M)*x(P4)^2)/(2*x(M)*y(P4)+(-(2*x(M)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(M)*x(P5)+(-(2*y(M)))*x(P4))" actx="1.0368540748281725" y="(-x(M)^2*x(P4)+x(M)^2*x(P5)+x(M)*x(P4)^2-x(M)*x(P5)^2+x(M)*y(P4)^2-x(M)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(M)^2-x(P5)*y(P4)^2+x(P5)*y(M)^2)/(2*x(M)*y(P4)+(-(2*x(M)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(M)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(M))" acty="-2.0313552101683543" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c3" n="10" color="2" target="true" through="P4" midpoint="P6" start="P5" end="P4">Cercle de centre P6 passant par P4</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_fixedangle" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="O">Origine</Parameter>
<Parameter name="A">Point de direction</Parameter>
<Parameter name="E1">Valeur de l&apos;angle</Parameter>
<Comment>
<P>la valeur de l&apos;angle est une expression</P>
</Comment>
<Objects>
<Point name="P17" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Expression name="E1" n="1" color="5" type="thick" showname="true" parameter="true" mainparameter="true" x="0.0" y="0.0" value="48" prompt="Valeur">Expression &quot;48&quot; à 0.0, 0.0</Expression>
<Point name="A" n="2" parameter="true" mainparameter="true" x="1.6075650118203306" y="-0.8510638297872335">Point</Point>
<Point name="O" n="3" parameter="true" mainparameter="true" x="-0.17021276595744705" y="-3.1394799054373532">Point</Point>
<Circle name="Hz" n="4" parameter="true" mainparameter="true" midpoint="P17">???</Circle>
<Point name="P1" n="5" color="2" hidden="super" x="(y(A)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(A)^2+y(A)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" actx="5.511822781439389" y="(x(O)*(x(A)^2+y(A)^2+Hz^2)-x(A)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(A)-x(A)*y(O)))" acty="-5.718873403973836" shape="circle" fixed="true">Point</Point>
<Point name="P2" n="6" color="2" hidden="super" x="x(P17)+Hz^2/d(P17,O)^2*(x(O)-x(P17))" actx="-0.4157798685716501" y="y(P17)+Hz^2/d(P17,O)^2*(y(O)-y(P17))" acty="-7.6688286869882045" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Segment name="s1" n="7" color="2" hidden="super" from="O" to="P1">Segment de O à P1</Segment>
<Midpoint name="P3" n="8" color="2" hidden="super" first="O" second="P2" shape="circle">Milieu de O et P2</Midpoint>
<Segment name="s2" n="9" color="2" hidden="super" from="P17" to="P2">Segment de P17 à P2</Segment>
<Plumb name="perp1" n="10" color="1" hidden="super" point="O" line="s1" valid="true">Perpendiculaire passant par O à s1</Plumb>
<Plumb name="perp2" n="11" color="1" hidden="super" point="P3" line="s2" valid="true">Perpendiculaire passant par P3 à s2</Plumb>
<Plumb name="perp3" n="12" color="1" hidden="super" point="A" line="perp1" valid="true">Perpendiculaire passant par A à perp1</Plumb>
<Intersection name="P4" n="13" color="2" hidden="super" first="perp1" second="perp3" shape="circle">Intersection entre perp1 et perp3</Intersection>
<Point name="P5" n="14" color="2" hidden="super" x="x(O)+cos(E1)*(x(P4)-x(O))-sin(E1)*(y(P4)-y(O))" actx="0.9948856659418341" y="y(O)+sin(E1)*(x(P4)-x(O))+cos(E1)*(y(P4)-y(O))" acty="-0.5729344326259302" shape="circle" fixed="true">Point</Point>
<Segment name="s3" n="15" color="2" hidden="super" from="O" to="P5">Segment de O à P5</Segment>
<Plumb name="perp4" n="16" color="1" hidden="super" point="O" line="s3" valid="true">Perpendiculaire passant par O à s3</Plumb>
<Intersection name="P6" n="17" color="2" hidden="super" first="perp4" second="perp2" shape="circle">Intersection entre perp4 et perp2</Intersection>
<Circle name="c2" n="18" color="3" hidden="super" through="O" midpoint="P6" acute="true">Cercle de centre P6 passant par O</Circle>
<Ray name="r1" n="19" color="1" hidden="super" from="P6" to="P5">Demi-droite d&apos;origine P6 vers P5</Ray>
<Intersection name="P7" n="20" color="5" hidden="super" first="r1" second="c2" shape="circle" which="first">Intersection entre r1 et c2</Intersection>
<Point name="P8" n="21" color="2" hidden="super" showname="true" x="(y(P7)*(x(O)^2+y(O)^2+Hz^2)-y(O)*(x(P7)^2+y(P7)^2+Hz^2))/(2*(x(O)*y(P7)-x(P7)*y(O)))" actx="-4.798266863566451" y="(x(O)*(x(P7)^2+y(P7)^2+Hz^2)-x(P7)*(x(O)^2+y(O)^2+Hz^2))/(2*(x(O)*y(P7)-x(P7)*y(O)))" acty="-5.159892640087977" shape="circle" fixed="true">Point</Point>
<Circle name="c3" n="22" color="2" hidden="super" fixed="d(P8,O)" midpoint="P8" acute="true">Cercle de centre P8 de rayon d(P8,O)</Circle>
<Intersection name="P9" n="23" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c3" closeto="O" shape="circle" which="first">Intersection entre Hz et c3</Intersection>
<Intersection name="P10" n="24" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c3" awayfrom="P9" shape="circle" which="second">Intersection entre Hz et c3</Intersection>
<Point name="P11" n="25" color="5" hidden="super" showname="true" x="if(d(O,P9)&lt;d(P7,P9),x(P10),x(P9))" actx="0.0" y="if(d(O,P9)&lt;d(P7,P9),y(P10),y(P9))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P12" n="26" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P11,O,P7)&lt;180,x(O),x(P11))" actx="0.0" y="if(a(P11,O,P7)&lt;180,y(O),y(P11))" acty="0.0" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P13" n="27" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P11,O,P7)&lt;180,x(P11),x(O))" actx="-0.17021276595744705" y="if(a(P11,O,P7)&lt;180,y(P11),y(O))" acty="-3.1394799054373532" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Point name="P14" n="28" hidden="super" x="(x(P7)^2*y(P12)-x(P7)^2*y(P13)+y(P12)^2*y(P13)-y(P12)^2*y(P7)-y(P12)*y(P13)^2+y(P12)*y(P7)^2-y(P12)*x(P13)^2+y(P13)^2*y(P7)-y(P13)*y(P7)^2+y(P13)*x(P12)^2+y(P7)*x(P13)^2-y(P7)*x(P12)^2)/(2*x(P7)*y(P12)+(-(2*x(P7)))*y(P13)+(-(2*y(P12)))*x(P13)+2*y(P13)*x(P12)+2*y(P7)*x(P13)+(-(2*y(P7)))*x(P12))" actx="-0.0807519442701392" y="(-x(P7)^2*x(P12)+x(P7)^2*x(P13)+x(P7)*x(P12)^2-x(P7)*x(P13)^2+x(P7)*y(P12)^2-x(P7)*y(P13)^2-x(P12)^2*x(P13)+x(P12)*x(P13)^2+x(P12)*y(P13)^2-x(P12)*y(P7)^2-x(P13)*y(P12)^2+x(P13)*y(P7)^2)/(2*x(P7)*y(P12)+(-(2*x(P7)))*y(P13)+2*x(P12)*y(P13)+(-(2*x(P12)))*y(P7)+(-(2*x(P13)))*y(P12)+2*x(P13)*y(P7))" acty="-1.5699760367450457" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c4" n="29" color="5" target="true" through="P12" midpoint="P14" start="P13" end="P12">Cercle de centre P14 passant par P12</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_circ" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="A">A</Parameter>
<Parameter name="B">B</Parameter>
<Parameter name="C">C</Parameter>
<Comment>
<P>Etant donnés trois points, construire le cercleou
l&apos;équidistante passant par ces trois points.</P>
</Comment>
<Objects>
<Point name="P8" n="0" hidden="true" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" mainparameter="true" x="-1.9479905437352247" y="-1.7399527186761228">Point</Point>
<Point name="B" n="2" mainparameter="true" x="-1.3283759574295235" y="0.7692366264980919">Point</Point>
<Point name="C" n="3" mainparameter="true" x="1.456264775413711" y="1.1725768321513002">Point</Point>
<Circle name="Hz" n="4" mainparameter="true" midpoint="P8">???</Circle>
<Point name="P1" alias="euclid" n="5" color="5" hidden="super" showname="true" x="(x(C)^2*y(A)-x(C)^2*y(B)+y(A)^2*y(B)-y(A)^2*y(C)-y(A)*y(B)^2+y(A)*y(C)^2-y(A)*x(B)^2+y(B)^2*y(C)-y(B)*y(C)^2+y(B)*x(A)^2+y(C)*x(B)^2-y(C)*x(A)^2)/(2*x(C)*y(A)+(-(2*x(C)))*y(B)+(-(2*y(A)))*x(B)+2*y(B)*x(A)+2*y(C)*x(B)+(-(2*y(C)))*x(A))" actx="0.3458404004899406" y="(-x(C)^2*x(A)+x(C)^2*x(B)+x(C)*x(A)^2-x(C)*x(B)^2+x(C)*y(A)^2-x(C)*y(B)^2-x(A)^2*x(B)+x(A)*x(B)^2+x(A)*y(B)^2-x(A)*y(C)^2-x(B)*y(A)^2+x(B)*y(C)^2)/(2*x(C)*y(A)+(-(2*x(C)))*y(B)+2*x(A)*y(B)+(-(2*x(A)))*y(C)+(-(2*x(B)))*y(A)+2*x(B)*y(C))" acty="-0.975289185061928" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Ray name="r1" n="6" color="1" hidden="super" from="P8" to="P1">Demi-droite d&apos;origine P8 vers P1</Ray>
<Intersection name="P2" n="7" color="5" hidden="super" showname="true" first="r1" second="Hz" shape="circle" which="first">Intersection entre r1 et Hz</Intersection>
<Circle name="c2" n="8" color="1" target="true" large="true" ctag0="thick" cexpr0="c2&lt;d(P1,P2)" ctag1="hidden" cexpr1="c2&gt;d(P1,P2)" ctag2="thin" cexpr2="c2&gt;d(P1,P2)" through="C" midpoint="P1">Cercle de centre P10 passant par P7 </Circle>
<Intersection name="P3" n="9" color="5" hidden="super" first="c2" second="Hz" shape="circle" which="second">Intersection entre c2 et Hz</Intersection>
<Intersection name="P4" n="10" color="5" hidden="super" first="c2" second="Hz" shape="circle" which="first">Intersection entre c2 et Hz</Intersection>
<Point name="P5" n="11" color="5" hidden="super" showname="true" x="(y(P4)*(x(P3)^2+y(P3)^2+Hz^2)-y(P3)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" actx="2.5205218738451647" y="(x(P3)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P3)^2+y(P3)^2+Hz^2))/(2*(x(P3)*y(P4)-x(P4)*y(P3)))" acty="-4.352620772162886" shape="circle" fixed="true">Point</Point>
<Point name="P6" n="12" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P4,P3,B)&lt;180,x(P3),x(P4))" actx="1.8549042864005085" y="if(a(P4,P3,B)&lt;180,y(P3),y(P4))" acty="-4.619870046212666" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P7" n="13" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P4,P3,B)&lt;180,x(P4),x(P3))" actx="3.0836244083600968" y="if(a(P4,P3,B)&lt;180,y(P4),y(P3))" acty="-3.9083410372900986" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Circle name="c3" n="14" color="2" type="thin" target="true" ctag0="hidden" cexpr0="c2&lt;d(P1,P2)" through="P3" midpoint="P5" start="P3" end="P4" acute="true">Cercle de centre P5 passant par P3</Circle>
<Point name="P9" n="15" hidden="super" x="(x(B)^2*y(P6)-x(B)^2*y(P7)+y(P6)^2*y(P7)-y(P6)^2*y(B)-y(P6)*y(P7)^2+y(P6)*y(B)^2-y(P6)*x(P7)^2+y(P7)^2*y(B)-y(P7)*y(B)^2+y(P7)*x(P6)^2+y(B)*x(P7)^2-y(B)*x(P6)^2)/(2*x(B)*y(P6)+(-(2*x(B)))*y(P7)+(-(2*y(P6)))*x(P7)+2*y(P7)*x(P6)+2*y(B)*x(P7)+(-(2*y(B)))*x(P6))" actx="0.897850749659893" y="(-x(B)^2*x(P6)+x(B)^2*x(P7)+x(B)*x(P6)^2-x(B)*x(P7)^2+x(B)*y(P6)^2-x(B)*y(P7)^2-x(P6)^2*x(P7)+x(P6)*x(P7)^2+x(P6)*y(P7)^2-x(P6)*y(B)^2-x(P7)*y(P6)^2+x(P7)*y(B)^2)/(2*x(B)*y(P6)+(-(2*x(B)))*y(P7)+2*x(P6)*y(P7)+(-(2*x(P6)))*y(B)+(-(2*x(P7)))*y(P6)+2*x(P7)*y(B))" acty="-1.5504740759538977" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c4" n="16" color="5" target="true" ctag0="thick" cexpr0="c2&gt;d(P1,P2)" ctag1="blue" cexpr1="c2==d(P1,P2)" through="P6" midpoint="P9" start="P7" end="P6">Cercle de centre P9 passant par P6</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceau1" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c3">droite 1</Parameter>
<Parameter name="c5">droite 2</Parameter>
<Parameter name="M">Point</Parameter>
<Comment>
<P>Construit la droite du pinceau défini par les deux droites
et passant par le point donné</P>
</Comment>
<Objects>
<Point name="P19" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P19">???</Circle>
<Point name="P1" n="2" parameter="true" x="-3.9888989375822583" y="2.2936410414433004">Point</Point>
<Circle name="c3" n="3" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P2" n="4" parameter="true" x="3.514730589065355" y="-2.8857465695153564">Point</Point>
<Circle name="c5" n="5" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Point name="M" n="6" parameter="true" mainparameter="true" x="-0.30055690620121744" y="-2.083861216328449">Point</Point>
<Line name="l1" n="7" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Point name="P3" n="8" color="1" hidden="super" x="x(P19)+Hz^2/d(P19,M)^2*(x(M)-x(P19))" actx="-0.8603578359001102" y="y(P19)+Hz^2/d(P19,M)^2*(y(M)-y(P19))" acty="-5.9651476622407875" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Midpoint name="P4" n="9" color="1" hidden="super" first="P3" second="M" shape="circle">Milieu de P3 et P14</Midpoint>
<Segment name="s1" n="10" color="2" hidden="super" from="P19" to="P3">Segment de P19 à P3</Segment>
<Plumb name="perp1" n="11" color="3" hidden="super" point="P4" line="s1" valid="true">Perpendiculaire passant par P4 à s1</Plumb>
<Intersection name="P5" n="12" color="1" hidden="super" showname="true" first="perp1" second="l1" shape="circle">Intersection entre perp1 et l1</Intersection>
<Circle name="c6" n="13" color="2" hidden="super" fixed="sqrt(d(P5,P19)^2-Hz^2)" midpoint="P5" acute="true">Cercle de centre P5 de rayon sqrt(d(P5,P19)^2-Hz^2)</Circle>
<Intersection name="P6" n="14" color="1" hidden="super" first="Hz" second="c6" shape="circle" which="first">Intersection entre Hz et c6</Intersection>
<Intersection name="P7" n="15" color="1" hidden="super" first="Hz" second="c6" shape="circle" which="second">Intersection entre Hz et c6</Intersection>
<Point name="P8" n="16" color="5" hidden="super" showname="true" x="(y(P7)*(x(P6)^2+y(P6)^2+Hz^2)-y(P6)*(x(P7)^2+y(P7)^2+Hz^2))/(2*(x(P6)*y(P7)-x(P7)*y(P6)))" actx="6.6820309108394715" y="(x(P6)*(x(P7)^2+y(P7)^2+Hz^2)-x(P7)*(x(P6)^2+y(P6)^2+Hz^2))/(2*(x(P6)*y(P7)-x(P7)*y(P6)))" acty="-5.071978710711075" shape="circle" fixed="true">Point</Point>
<Circle name="c7" n="17" color="2" target="true" through="P6" midpoint="P8" start="P6" end="P7" acute="true">Cercle de centre P8 passant par P6</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceau3" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c3">Droite 1 du pinceau</Parameter>
<Parameter name="c4">Droite 2 du pinceau</Parameter>
<Parameter name="c6">Droite 3 du pinceau</Parameter>
<Comment>
<P>Attention le produit n&apos;est pas commutatif. Ne s&apos;applique a
priori que sur trois droites dont on sait qu&apos;elles sont en
pinceau</P>
</Comment>
<Objects>
<Point name="P23" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P23">???</Circle>
<Point name="P1" n="2" parameter="true" x="-4.505823196634828" y="0.8537096428615037">Point</Point>
<Circle name="c3" n="3" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P2" n="4" parameter="true" x="-1.2756455899464165" y="-5.672063173976182">Point</Point>
<Circle name="c4" n="5" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Point name="P3" n="6" parameter="true" x="-1.968219773946281" y="-4.272888783617086">Point</Point>
<Circle name="c6" n="7" parameter="true" mainparameter="true" midpoint="P3">???</Circle>
<Line name="l1" n="8" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Point name="P4" n="9" color="2" hidden="super" x="x(P23)+Hz^2/d(P23,P1)^2*(x(P1)-x(P23))" actx="-3.332105465476584" y="y(P23)+Hz^2/d(P23,P1)^2*(y(P1)-y(P23))" acty="0.6313276049165456" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P5" n="10" color="2" hidden="super" x="x(P23)+Hz^2/d(P23,P2)^2*(x(P2)-x(P23))" actx="-0.5869873882910768" y="y(P23)+Hz^2/d(P23,P2)^2*(y(P2)-y(P23))" acty="-2.6099957346727685" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P6" n="11" color="2" hidden="super" x="x(P3)+c6^2/d(P3,P5)^2*(x(P5)-x(P3))" actx="-0.023736524877505882" y="y(P3)+c6^2/d(P3,P5)^2*(y(P5)-y(P3))" acty="-1.9318868550159882" shape="dcross" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Line name="l2" n="12" color="3" hidden="super" from="P6" to="P4">Droite passant par P6 et P4</Line>
<Intersection name="P7" n="13" color="2" hidden="super" showname="true" first="l2" second="l1" shape="dcross">Intersection entre l2 et l1</Intersection>
<Circle name="c7" n="14" color="2" hidden="super" fixed="sqrt(d(P7,P23)^2-Hz^2)" midpoint="P7" acute="true">Cercle de centre P7 de rayon sqrt(d(P7,P23)^2-Hz^2)</Circle>
<Intersection name="P8" n="15" color="2" hidden="super" first="Hz" second="c7" shape="dcross" which="first">Intersection entre Hz et c7</Intersection>
<Intersection name="P9" n="16" color="2" hidden="super" first="Hz" second="c7" shape="dcross" which="second">Intersection entre Hz et c7</Intersection>
<Point name="P10" n="17" color="5" hidden="super" showname="true" x="(y(P8)*(x(P9)^2+y(P9)^2+Hz^2)-y(P9)*(x(P8)^2+y(P8)^2+Hz^2))/(2*(x(P9)*y(P8)-x(P8)*y(P9)))" actx="-5.057395794200762" y="(x(P9)*(x(P8)^2+y(P8)^2+Hz^2)-x(P8)*(x(P9)^2+y(P9)^2+Hz^2))/(2*(x(P9)*y(P8)-x(P8)*y(P9)))" acty="1.9680252941914245" shape="circle" fixed="true">Point</Point>
<Circle name="c8" n="18" color="2" target="true" through="P9" midpoint="P10" start="P9" end="P8" acute="true">Cercle de centre P10 passant par P9</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceauinter" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c11">Pinceau 1 - droite 1</Parameter>
<Parameter name="c13">Pinceau 1 - droite 2</Parameter>
<Parameter name="c15">Pinceau 2 - droite 1</Parameter>
<Parameter name="c17">Pinceau 2 - droite 2</Parameter>
<Comment>
<P>L&apos;intersection de deux pinceaux peut, dans certains cas, ne
pas exister. Existe toujours si un pinceau est à centre.</P>
</Comment>
<Objects>
<Point name="P31" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P31">???</Circle>
<Point name="P1" n="2" parameter="true" x="-4.977309972535444" y="3.4673243792116746">Point</Point>
<Circle name="c11" n="3" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P2" n="4" parameter="true" x="-1.4329301525504716" y="-5.6956513002001286">Point</Point>
<Circle name="c13" n="5" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Line name="l1" n="6" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Point name="P3" n="7" parameter="true" x="3.846949504076278" y="3.8565280533626782">Point</Point>
<Circle name="c15" n="8" parameter="true" mainparameter="true" midpoint="P3">???</Circle>
<Point name="P4" n="9" parameter="true" x="4.31740278048211" y="-3.0535487654484372">Point</Point>
<Circle name="c17" n="10" parameter="true" mainparameter="true" midpoint="P4">???</Circle>
<Line name="l2" n="11" color="3" hidden="super" from="P3" to="P4">Droite passant par P3 et P4</Line>
<Intersection name="P5" n="12" scolor="128,,128,,128" hidden="super" first="l1" second="l2" shape="circle">Intersection entre l1 et l2</Intersection>
<Midpoint name="P6" n="13" scolor="128,,128,,128" hidden="super" first="P31" second="P5" shape="circle">Milieu de P31 et P5</Midpoint>
<Circle name="c6" n="14" scolor="255,,46,,222" hidden="super" through="P31" midpoint="P6" acute="true">Cercle de centre P6 passant par P31</Circle>
<Intersection name="P7" n="15" scolor="128,,128,,128" hidden="super" first="Hz" second="c6" shape="circle" which="first">Intersection entre Hz et c6</Intersection>
<Intersection name="P8" n="16" scolor="128,,128,,128" hidden="super" first="Hz" second="c6" shape="circle" which="second">Intersection entre Hz et c6</Intersection>
<Point name="P9" n="17" color="5" hidden="super" showname="true" x="(y(P8)*(x(P7)^2+y(P7)^2+Hz^2)-y(P7)*(x(P8)^2+y(P8)^2+Hz^2))/(2*(x(P7)*y(P8)-x(P8)*y(P7)))" actx="5.76399140732991" y="(x(P7)*(x(P8)^2+y(P8)^2+Hz^2)-x(P8)*(x(P7)^2+y(P7)^2+Hz^2))/(2*(x(P7)*y(P8)-x(P8)*y(P7)))" acty="-24.301224226664303" shape="circle" fixed="true">Point</Point>
<Circle name="c7" n="18" color="5" target="true" through="P7" midpoint="P9" start="P7" end="P8" acute="true">Cercle de centre P9 passant par P7</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceauhauteur" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c22">Droite 1 du pinceau</Parameter>
<Parameter name="c24">Droite 2 du pinceau</Parameter>
<Parameter name="c11">Droite orthogonale au pinceau</Parameter>
<Comment>
<P>La hauteur d&apos;un pinceau peut, dans des cas particuliers, ne
pas exister. Elle existe toujours si le pinceau est à
centre.</P>
</Comment>
<Objects>
<Point name="P24" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P24">???</Circle>
<Point name="P1" n="2" parameter="true" x="-3.8567626987612282" y="2.943594695884522">Point</Point>
<Point name="P2" n="3" parameter="true" x="-0.6437522971529172" y="-4.629749411943832">Point</Point>
<Point name="P3" n="4" parameter="true" x="5.0237089341572" y="-0.6457703649787292">Point</Point>
<Circle name="c22" n="5" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Circle name="c24" n="6" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Line name="l1" n="7" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Circle name="c11" n="8" parameter="true" mainparameter="true" midpoint="P3">???</Circle>
<Circle name="c5" n="1036" color="3" hidden="super" fixed="c11" midpoint="P3" acute="true">Cercle de centre P3 de rayon c11</Circle>
<Intersection name="P4" n="9" hidden="super" first="Hz" second="c5" shape="circle" which="first">Intersection entre Hz et c5</Intersection>
<Intersection name="P5" n="10" hidden="super" first="Hz" second="c5" shape="circle" which="second">Intersection entre Hz et c5</Intersection>
<Line name="l2" n="11" color="3" hidden="super" from="P5" to="P4">Droite passant par P5 et P4</Line>
<Intersection name="P6" n="12" hidden="super" showname="true" first="l1" second="l2" shape="circle">Intersection entre l1 et l2</Intersection>
<Circle name="c6" n="1045" color="3" hidden="super" fixed="sqrt(d(P6,P24)^2-Hz^2)" midpoint="P6" acute="true">Cercle de centre P6 de rayon sqrt(d(P6,P24)^2-Hz^2)</Circle>
<Intersection name="P7" n="13" hidden="super" first="Hz" second="c6" shape="circle" which="first">Intersection entre Hz et c6</Intersection>
<Intersection name="P8" n="14" hidden="super" first="Hz" second="c6" shape="circle" which="second">Intersection entre Hz et c6</Intersection>
<Point name="P9" n="15" color="5" hidden="super" showname="true" x="(y(P8)*(x(P7)^2+y(P7)^2+Hz^2)-y(P7)*(x(P8)^2+y(P8)^2+Hz^2))/(2*(x(P7)*y(P8)-x(P8)*y(P7)))" actx="1.492944715110579" y="(x(P7)*(x(P8)^2+y(P8)^2+Hz^2)-x(P8)*(x(P7)^2+y(P7)^2+Hz^2))/(2*(x(P7)*y(P8)-x(P8)*y(P7)))" acty="-9.666129536931953" shape="circle" fixed="true">Point</Point>
<Circle name="c7" n="16" color="3" target="true" through="P7" midpoint="P9" start="P7" end="P8" acute="true">Cercle de centre P9 passant par P7</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceaucycle" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c3">Droite 1 du pinceau</Parameter>
<Parameter name="c5">Droite 2 du pinceau</Parameter>
<Parameter name="A">A</Parameter>
<Comment>
<P>Construit le cycle (cercle ou équidistante) défini par un
pinceau (2 droites) et un point.</P>
</Comment>
<Objects>
<Point name="P41" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Point name="A" n="1" parameter="true" mainparameter="true" x="-3.158392434988179" y="0.5295508274231686">Point</Point>
<Circle name="Hz" n="2" parameter="true" mainparameter="true" midpoint="P41">???</Circle>
<Point name="P1" n="3" parameter="true" x="-4.601292751434766" y="-3.7657011084420744">Point</Point>
<Point name="P2" n="4" parameter="true" x="2.9352292445800985" y="-6.405075125955195">Point</Point>
<Circle name="c3" n="5" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Circle name="c5" n="6" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Line name="l1" n="7" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Line name="l2" n="8" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Line name="l3" n="9" color="3" hidden="super" from="P1" to="P2">Droite passant par P1 et P2</Line>
<Parallel name="par1" n="10" color="3" hidden="super" point="A" line="l1">Parallèle passant par A à l1</Parallel>
<Intersection name="P3" n="11" color="2" hidden="super" first="par1" second="Hz" shape="circle" which="first">Intersection entre par1 et Hz</Intersection>
<Intersection name="P4" n="12" color="2" hidden="super" first="par1" second="Hz" shape="circle" which="second">Intersection entre par1 et Hz</Intersection>
<Midpoint name="P5" n="13" color="2" hidden="super" first="P4" second="P3" shape="circle">Milieu de P4 et P3</Midpoint>
<Midpoint name="P6" n="14" color="2" hidden="super" first="P5" second="P3" shape="circle">Milieu de P5 et P3</Midpoint>
<Midpoint name="P7" n="15" color="2" hidden="super" first="P6" second="P5" shape="circle">Milieu de P6 et P5</Midpoint>
<Midpoint name="P8" n="16" color="2" hidden="super" first="P6" second="P3" shape="circle">Milieu de P6 et P3</Midpoint>
<Point name="P9" n="17" color="1" hidden="super" x="x(P41)+Hz^2/d(P41,P7)^2*(x(P7)-x(P41))" actx="13.312535955304986" y="y(P41)+Hz^2/d(P41,P7)^2*(y(P7)-y(P41))" acty="-12.474476221929567" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P10" n="18" color="1" hidden="super" x="x(P41)+Hz^2/d(P41,P8)^2*(x(P8)-x(P41))" actx="5.824759594130056" y="y(P41)+Hz^2/d(P41,P8)^2*(y(P8)-y(P41))" acty="-3.055359016674954" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Midpoint name="P11" n="19" color="1" hidden="super" first="P9" second="P7" shape="circle">Milieu de P9 et P7</Midpoint>
<Segment name="s1" n="20" color="2" hidden="super" from="P41" to="P9">Segment de P41 à P9</Segment>
<Midpoint name="P12" n="21" color="1" hidden="super" first="P10" second="P8" shape="circle">Milieu de P10 et P8</Midpoint>
<Segment name="s2" n="22" color="2" hidden="super" from="P41" to="P10">Segment de P41 à P10</Segment>
<Plumb name="perp1" n="23" color="3" hidden="super" point="P11" line="s1" valid="true">Perpendiculaire passant par P11 à s1</Plumb>
<Plumb name="perp2" n="24" color="3" hidden="super" point="P12" line="s2" valid="true">Perpendiculaire passant par P12 à s2</Plumb>
<Intersection name="P13" n="25" color="1" hidden="super" showname="true" first="perp1" second="l2" shape="circle">Intersection entre perp1 et l2</Intersection>
<Intersection name="P14" n="26" color="1" hidden="super" showname="true" first="perp2" second="l3" shape="circle">Intersection entre perp2 et l3</Intersection>
<Circle name="c6" n="13" color="2" hidden="super" fixed="sqrt(d(P13,P41)^2-Hz^2)" midpoint="P13" acute="true">Cercle de centre P13 de rayon sqrt(d(P13,P41)^2-Hz^2)</Circle>
<Circle name="c7" n="13" color="2" hidden="super" fixed="sqrt(d(P14,P41)^2-Hz^2)" midpoint="P14" acute="true">Cercle de centre P14 de rayon sqrt(d(P14,P41)^2-Hz^2)</Circle>
<Intersection name="P15" n="27" color="1" hidden="super" first="Hz" second="c6" shape="circle" which="first">Intersection entre Hz et c6</Intersection>
<Intersection name="P16" n="28" color="1" hidden="super" first="Hz" second="c6" shape="circle" which="second">Intersection entre Hz et c6</Intersection>
<Intersection name="P17" n="29" color="1" hidden="super" first="Hz" second="c7" shape="circle" which="first">Intersection entre Hz et c7</Intersection>
<Intersection name="P18" n="30" color="1" hidden="super" first="Hz" second="c7" shape="circle" which="second">Intersection entre Hz et c7</Intersection>
<Point name="P19" n="31" color="5" hidden="super" showname="true" x="(y(P16)*(x(P15)^2+y(P15)^2+Hz^2)-y(P15)*(x(P16)^2+y(P16)^2+Hz^2))/(2*(x(P15)*y(P16)-x(P16)*y(P15)))" actx="6.313080861756133" y="(x(P15)*(x(P16)^2+y(P16)^2+Hz^2)-x(P16)*(x(P15)^2+y(P15)^2+Hz^2))/(2*(x(P15)*y(P16)-x(P16)*y(P15)))" acty="-7.5880364173975" shape="circle" fixed="true">Point</Point>
<Point name="P20" n="32" color="5" hidden="super" showname="true" x="(y(P18)*(x(P17)^2+y(P17)^2+Hz^2)-y(P17)*(x(P18)^2+y(P18)^2+Hz^2))/(2*(x(P17)*y(P18)-x(P18)*y(P17)))" actx="2.5358725530146073" y="(x(P17)*(x(P18)^2+y(P18)^2+Hz^2)-x(P18)*(x(P17)^2+y(P17)^2+Hz^2))/(2*(x(P17)*y(P18)-x(P18)*y(P17)))" acty="-6.265215960680375" shape="circle" fixed="true">Point</Point>
<Circle name="c8" n="33" color="2" scolor="255,,102,,212" hidden="super" through="P15" midpoint="P19" start="P15" end="P16" acute="true">Cercle de centre P19 passant par P15</Circle>
<Circle name="c9" n="34" color="2" scolor="255,,102,,212" hidden="super" through="P17" midpoint="P20" start="P17" end="P18" acute="true">Cercle de centre P20 passant par P17</Circle>
<Point name="P21" n="35" color="2" hidden="super" x="x(P19)+c8^2/d(P19,A)^2*(x(A)-x(P19))" actx="1.8775717323818224" y="y(P19)+c8^2/d(P19,A)^2*(y(A)-y(P19))" acty="-3.786554725497363" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P22" n="36" color="2" hidden="super" x="x(P20)+c9^2/d(P20,A)^2*(x(A)-x(P20))" actx="1.005687081498015" y="y(P20)+c9^2/d(P20,A)^2*(y(A)-y(P20))" acty="-4.43929928075673" shape="circle" fixed="true">Point à &quot;x(P1)+c1^2/d(P1,A)^2*(x(A)-x(P1))&quot;, &quot;y(P1)+c1^2/d(P1,A)^2*(y(A)-y(P1))&quot; </Point>
<Point name="P23" alias="euclid" n="37" color="5" hidden="super" showname="true" x="(x(P22)^2*y(A)-x(P22)^2*y(P21)+y(A)^2*y(P21)-y(A)^2*y(P22)-y(A)*y(P21)^2+y(A)*y(P22)^2-y(A)*x(P21)^2+y(P21)^2*y(P22)-y(P21)*y(P22)^2+y(P21)*x(A)^2+y(P22)*x(P21)^2-y(P22)*x(A)^2)/(2*x(P22)*y(A)+(-(2*x(P22)))*y(P21)+(-(2*y(A)))*x(P21)+2*y(P21)*x(A)+2*y(P22)*x(P21)+(-(2*y(P22)))*x(A))" actx="-0.521889404586572" y="(-x(P22)^2*x(A)+x(P22)^2*x(P21)+x(P22)*x(A)^2-x(P22)*x(P21)^2+x(P22)*y(A)^2-x(P22)*y(P21)^2-x(A)^2*x(P21)+x(A)*x(P21)^2+x(A)*y(P21)^2-x(A)*y(P22)^2-x(P21)*y(A)^2+x(P21)*y(P22)^2)/(2*x(P22)*y(A)+(-(2*x(P22)))*y(P21)+2*x(A)*y(P21)+(-(2*x(A)))*y(P22)+(-(2*x(P21)))*y(A)+2*x(P21)*y(P22))" acty="-1.4902135699811858" shape="circle" fixed="true">Point à &quot;(x(A)^2*y(P4)-x(A)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(A)-y(P4)*y(P5)^2+y(P4)*y(A)^2-y(P4)*x(P5)^2+y(P5)^2*y(A)-y(P5)*y(A)^2+y(P5)*x(P4)^2+y(A)*x(P5)^2-y(A)*x(P4)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(A)*x(P5)+(-(2*y(A)))*x(P4))&quot;, &quot;(-x(A)^2*x(P4)+x(A)^2*x(P5)+x(A)*x(P4)^2-x(A)*x(P5)^2+x(A)*y(P4)^2-x(A)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(A)^2-x(P5)*y(P4)^2+x(P5)*y(A)^2)/(2*x(A)*y(P4)+(-(2*x(A)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(A)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(A))&quot; </Point>
<Ray name="r1" n="38" color="1" hidden="super" from="P41" to="P23">Demi-droite d&apos;origine P41 vers P23</Ray>
<Intersection name="P24" n="39" color="5" hidden="super" showname="true" first="r1" second="Hz" shape="circle" which="first">Intersection entre r1 et Hz</Intersection>
<Circle name="c10" n="40" color="1" scolor="255,,102,,212" target="true" large="true" ctag0="hidden" cexpr0="c10&gt;d(P23,P24)" through="P22" midpoint="P23">Cercle de centre P10 passant par P7 </Circle>
<Intersection name="P25" n="41" color="5" hidden="super" first="c10" second="Hz" shape="circle" which="second">Intersection entre c10 et Hz</Intersection>
<Intersection name="P26" n="42" color="5" hidden="super" first="c10" second="Hz" shape="circle" which="first">Intersection entre c10 et Hz</Intersection>
<Point name="P27" n="43" color="5" hidden="super" showname="true" x="(y(P26)*(x(P25)^2+y(P25)^2+Hz^2)-y(P25)*(x(P26)^2+y(P26)^2+Hz^2))/(2*(x(P25)*y(P26)-x(P26)*y(P25)))" actx="-1.7514158931878872" y="(x(P25)*(x(P26)^2+y(P26)^2+Hz^2)-x(P26)*(x(P25)^2+y(P25)^2+Hz^2))/(2*(x(P25)*y(P26)-x(P26)*y(P25)))" acty="-4.637639314742265" shape="circle" fixed="true">Point</Point>
<Point name="P28" n="44" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P26,P25,P21)&lt;180,x(P25),x(P26))" actx="-1.851045042634576" y="if(a(P26,P25,P21)&lt;180,y(P25),y(P26))" acty="-4.597526869854918" shape="circle" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(A),x(C))&quot;, &quot;if(a(C,A,B)&lt;180,y(A),y(C))&quot; </Point>
<Point name="P29" n="45" type="thick" hidden="super" showname="true" bold="true" large="true" x="if(a(P26,P25,P21)&lt;180,x(P26),x(P25))" actx="-1.6501426003916198" y="if(a(P26,P25,P21)&lt;180,y(P26),y(P25))" acty="-4.673398171274601" shape="diamond" fixed="true">Point à &quot;if(a(C,A,B)&lt;180,x(C),x(A))&quot;, &quot;if(a(C,A,B)&lt;180,y(C),y(A))&quot; </Point>
<Circle name="c11" n="46" color="2" scolor="255,,102,,212" target="true" ctag0="hidden" cexpr0="c10&lt;d(P23,P24)" ctag1="thin" cexpr1="c10&gt;d(P23,P24)" ctag2="black" cexpr2="c10&gt;d(P23,P24)" through="P25" midpoint="P27" start="P25" end="P26" acute="true">Cercle de centre P27 passant par P25</Circle>
<Point name="P30" n="47" hidden="super" x="(x(P21)^2*y(P28)-x(P21)^2*y(P29)+y(P28)^2*y(P29)-y(P28)^2*y(P21)-y(P28)*y(P29)^2+y(P28)*y(P21)^2-y(P28)*x(P29)^2+y(P29)^2*y(P21)-y(P29)*y(P21)^2+y(P29)*x(P28)^2+y(P21)*x(P29)^2-y(P21)*x(P28)^2)/(2*x(P21)*y(P28)+(-(2*x(P21)))*y(P29)+(-(2*y(P28)))*x(P29)+2*y(P29)*x(P28)+2*y(P21)*x(P29)+(-(2*y(P21)))*x(P28))" actx="-0.5701434528530482" y="(-x(P21)^2*x(P28)+x(P21)^2*x(P29)+x(P21)*x(P28)^2-x(P21)*x(P29)^2+x(P21)*y(P28)^2-x(P21)*y(P29)^2-x(P28)^2*x(P29)+x(P28)*x(P29)^2+x(P28)*y(P29)^2-x(P28)*y(P21)^2-x(P29)*y(P28)^2+x(P29)*y(P21)^2)/(2*x(P21)*y(P28)+(-(2*x(P21)))*y(P29)+2*x(P28)*y(P29)+(-(2*x(P28)))*y(P21)+(-(2*x(P29)))*y(P28)+2*x(P29)*y(P21))" acty="-1.5097040641679447" fixed="true">Point à &quot;(x(B)^2*y(P4)-x(B)^2*y(P5)+y(P4)^2*y(P5)-y(P4)^2*y(B)-y(P4)*y(P5)^2+y(P4)*y(B)^2-y(P4)*x(P5)^2+y(P5)^2*y(B)-y(P5)*y(B)^2+y(P5)*x(P4)^2+y(B)*x(P5)^2-y(B)*x(P4)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+(-(2*y(P4)))*x(P5)+2*y(P5)*x(P4)+2*y(B)*x(P5)+(-(2*y(B)))*x(P4))&quot;, &quot;(-x(B)^2*x(P4)+x(B)^2*x(P5)+x(B)*x(P4)^2-x(B)*x(P5)^2+x(B)*y(P4)^2-x(B)*y(P5)^2-x(P4)^2*x(P5)+x(P4)*x(P5)^2+x(P4)*y(P5)^2-x(P4)*y(B)^2-x(P5)*y(P4)^2+x(P5)*y(B)^2)/(2*x(B)*y(P4)+(-(2*x(B)))*y(P5)+2*x(P4)*y(P5)+(-(2*x(P4)))*y(B)+(-(2*x(P5)))*y(P4)+2*x(P5)*y(B))&quot; </Point>
<Circle name="c12" n="48" color="5" scolor="255,,102,,212" target="true" ctag0="blue" cexpr0="c10==d(P23,P24)" through="P28" midpoint="P30" start="P29" end="P28">Cercle de centre P30 passant par P28</Circle>
</Objects>
</Macro>
<Macro Name="@builtin@/DP_bi_pinceaubiss" showduplicates="true">
<Parameter name="Hz">=Hz</Parameter>
<Parameter name="c35">Droite 1</Parameter>
<Parameter name="P6">Pt idéal 1 de Drte 1</Parameter>
<Parameter name="P7">Pt idéal 2 de Drte 1</Parameter>
<Parameter name="c37">Droite 2</Parameter>
<Parameter name="P15">Pt idéal 1 de Drte 2</Parameter>
<Parameter name="P16">Pt idéal 2 de Drte 2</Parameter>
<Comment>
<P>Attention cette macro ne s&apos;applique QUE sur des droites
orientées (pour l&apos;orientation des points idéaux)</P>
</Comment>
<Objects>
<Point name="P25" n="0" parameter="true" x="0.0" y="0.0">Point</Point>
<Circle name="Hz" n="1" parameter="true" mainparameter="true" midpoint="P25">???</Circle>
<Point name="P6" n="2" parameter="true" mainparameter="true" x="-4.599927528367886" y="-0.12625961097401972">Point</Point>
<Point name="P7" n="3" parameter="true" mainparameter="true" x="-3.1399563227566922" y="3.3639187039493503">Point</Point>
<Point name="P1" n="4" hidden="true" parameter="true" x="-4.656862304748045" y="1.9480050186426097">Point</Point>
<Circle name="c35" n="5" parameter="true" mainparameter="true" midpoint="P1">???</Circle>
<Point name="P15" n="6" parameter="true" mainparameter="true" x="-3.543732145385201" y="-2.9355131131309227">Point</Point>
<Point name="P16" n="7" parameter="true" mainparameter="true" x="4.389465969440203" y="-1.3812542339216103">Point</Point>
<Point name="P2" n="8" hidden="true" parameter="true" x="1.8510471472807395" y="-9.448055234357717">Point</Point>
<Circle name="c37" n="9" parameter="true" mainparameter="true" midpoint="P2">???</Circle>
<Line name="l1" n="10" color="3" hidden="super" from="P15" to="P6">Droite passant par P15 et P6</Line>
<Line name="l2" n="11" color="3" hidden="super" from="P16" to="P7">Droite passant par P16 et P7</Line>
<Intersection name="P3" n="12" scolor="128,,128,,128" hidden="super" showname="true" first="l1" second="l2" shape="circle">Intersection entre l1 et l2</Intersection>
<Circle name="c4" n="13" color="2" hidden="super" fixed="sqrt(d(P25,P3)^2-Hz^2)" midpoint="P3" acute="true">Cercle de centre P3 de rayon sqrt(d(P25,P3)^2-Hz^2)</Circle>
<Intersection name="P4" n="14" scolor="128,,128,,128" hidden="super" first="l2" second="c4" closeto="P25" shape="circle" which="second">Intersection entre l2 et c4</Intersection>
<Intersection name="P5" n="15" scolor="128,,128,,128" hidden="super" first="l1" second="c4" closeto="P25" shape="circle" which="second">Intersection entre l1 et c4</Intersection>
<Point name="P8" n="16" color="2" hidden="super" showname="true" x="(y(P4)*(x(P5)^2+y(P5)^2+Hz^2)-y(P5)*(x(P4)^2+y(P4)^2+Hz^2))/(2*(x(P5)*y(P4)-x(P4)*y(P5)))" actx="-6.772940041181705" y="(x(P5)*(x(P4)^2+y(P4)^2+Hz^2)-x(P4)*(x(P5)^2+y(P5)^2+Hz^2))/(2*(x(P5)*y(P4)-x(P4)*y(P5)))" acty="5.653488255953618" shape="circle" fixed="true">Point</Point>
<Circle name="c5" n="17" color="2" hidden="super" fixed="d(P8,P5)" midpoint="P8" acute="true">Cercle de centre P8 de rayon d(P8,P5)</Circle>
<Intersection name="P9" n="18" color="2" hidden="super" showname="true" xcoffset="-0.500329544550496" ycoffset="0.17532500561225017" keepclose="true" first="Hz" second="c5" closeto="P5" shape="circle" which="first">Intersection entre Hz et c5</Intersection>
<Intersection name="P10" n="19" color="2" hidden="super" showname="true" xcoffset="-0.16089860051932625" ycoffset="0.4409946231703745" keepclose="true" first="Hz" second="c5" awayfrom="P9" shape="circle" which="second">Intersection entre Hz et c5</Intersection>
<Point name="P11" n="20" color="5" scolor="128,,128,,128" target="true" x="if(d(P5,P9)&lt;d(P4,P9),x(P9),x(P10))" actx="0.0" y="if(d(P5,P9)&lt;d(P4,P9),y(P9),y(P10))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P12" n="21" color="5" scolor="128,,128,,128" target="true" x="if(d(P5,P9)&lt;d(P4,P9),x(P10),x(P9))" actx="0.0" y="if(d(P5,P9)&lt;d(P4,P9),y(P10),y(P9))" acty="0.0" shape="circle" fixed="true">Point</Point>
<Point name="P13" n="22" color="5" hidden="super" showname="true" x="(y(P12)*(x(P11)^2+y(P11)^2+Hz^2)-y(P11)*(x(P12)^2+y(P12)^2+Hz^2))/(2*(x(P11)*y(P12)-x(P12)*y(P11)))" actx="-6.772940041181704" y="(x(P11)*(x(P12)^2+y(P12)^2+Hz^2)-x(P12)*(x(P11)^2+y(P11)^2+Hz^2))/(2*(x(P11)*y(P12)-x(P12)*y(P11)))" acty="5.653488255953616" shape="circle" fixed="true">Point</Point>
<Circle name="c6" n="23" color="5" target="true" through="P11" midpoint="P13" start="P11" end="P12" acute="true">Cercle de centre P13 passant par P11</Circle>
</Objects>
</Macro>
</CaR>
