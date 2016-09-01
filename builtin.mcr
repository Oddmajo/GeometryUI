<?xml version="1.0" encoding="utf-8"?>
<CaR>
<Macro Name="@builtin@/3Ddode" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="t">t</Parameter>
<Objects>
<Expression name="E1" n="0" color="1" type="thick" hidden="super" showname="true" showvalue="true" bold="true" x="-7.081614349775784" y="1.0834080717488792" value="(1+sqrt(5))/2" prompt="fi">Expression &quot;(1+sqrt(5))/2&quot; à -7.08161, 1.08341 </Expression>
<Point name="t" n="1" showname="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(t)-windowcx)+windowcx+d(windowcx)" actx="27.874625153493675" y="(windoww/(windoww-d(windoww)))*(y(t)-windowcy)+windowcy+d(windowcy)" acty="0.9733581650193237" shape="dcross" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(t)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(t)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="2" mainparameter="true" x="33.49485903558816" y="0.30428270286520764">Point à 33.49486, 0.30428</Point>
<Expression name="E2" n="3" color="1" type="thick" showname="true" showvalue="true" x="x(t)" y="y(t)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 27.87463, 1.12336</Expression>
<Point name="X" n="4" mainparameter="true" x="32.509744110792894" y="0.2620342829907925">Point à 32.50974, 0.26203</Point>
<Point name="Y" n="5" mainparameter="true" x="33.66675605401952" y="0.062163616989825705">Point à 33.66676, 0.06216</Point>
<Point name="Z" n="6" mainparameter="true" x="33.49485903558816" y="1.2736089736703404">Point à 33.49486, 1.27361</Point>
<Point name="P6" n="7" hidden="super" x="x(t)+E2*(x(Z)-x(O))" actx="27.874625153493675" y="y(t)+E2*(y(Z)-y(O))" acty="2.912010706629589" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(Z)-x(O))&quot;, &quot;y(t)+E2*(y(Z)-y(O))&quot;</Point>
<Point name="P7" n="8" hidden="super" x="x(t)+E2*(x(Y)-x(O))" actx="28.218419190356393" y="y(t)+E2*(y(Y)-y(O))" acty="0.4891199932685598" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(Y)-x(O))&quot;, &quot;y(t)+E2*(y(Y)-y(O))&quot;</Point>
<Point name="P8" n="9" hidden="super" x="x(t)+E2*(x(X)-x(O))" actx="25.904395303903136" y="y(t)+E2*(y(X)-y(O))" acty="0.8888613252704934" shape="dcross" fixed="true">Point à &quot;x(t)+E2*(x(X)-x(O))&quot;, &quot;y(t)+E2*(y(X)-y(O))&quot;</Point>
<Point name="P9" n="10" color="3" hidden="super" showname="true" x="x(t)+cos(72)*(x(P8)-x(t))+sin(72)*(x(P7)-x(t))" actx="27.592758206167105" y="y(t)+cos(72)*(y(P8)-y(t))+sin(72)*(y(P7)-y(t))" acty="0.4867093368835428" fixed="true">Point à &quot;x(P76)+cos(72)*(x(P13)-x(P76))+sin(72)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(72)*(y(P13)-y(P76))+sin(72)*(y(P14)-y(P76))&quot; </Point>
<Point name="P10" n="11" color="3" hidden="super" showname="true" x="x(t)+cos(144)*(x(P8)-x(t))+sin(144)*(x(P7)-x(t))" actx="29.67065164933122" y="y(t)+cos(144)*(y(P8)-y(t))+sin(144)*(y(P7)-y(t))" acty="0.7570894883949353" fixed="true">Point à &quot;x(P76)+cos(144)*(x(P13)-x(P76))+sin(144)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(144)*(y(P13)-y(P76))+sin(144)*(y(P14)-y(P76))&quot; </Point>
<Point name="P11" n="12" color="3" hidden="super" showname="true" x="x(t)+cos(216)*(x(P8)-x(t))+sin(216)*(x(P7)-x(t))" actx="29.266497519943215" y="y(t)+cos(216)*(y(P8)-y(t))+sin(216)*(y(P7)-y(t))" acty="1.3263456002992726" fixed="true">Point à &quot;x(P76)+cos(216)*(x(P13)-x(P76))+sin(216)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(216)*(y(P13)-y(P76))+sin(216)*(y(P14)-y(P76))&quot; </Point>
<Point name="P12" n="13" color="3" hidden="super" showname="true" x="x(t)+cos(288)*(x(P8)-x(t))+sin(288)*(x(P7)-x(t))" actx="26.938823088123698" y="y(t)+cos(288)*(y(P8)-y(t))+sin(288)*(y(P7)-y(t))" acty="1.4077850742483744" fixed="true">Point à &quot;x(P76)+cos(288)*(x(P13)-x(P76))+sin(288)*(x(P14)-x(P76))&quot;, &quot;y(P76)+cos(288)*(y(P13)-y(P76))+sin(288)*(y(P14)-y(P76))&quot; </Point>
<Point name="P13" n="14" color="1" hidden="super" showname="true" x="x(t)+E1*cos(72)*(x(P8)-x(t))+E1*sin(72)*(x(P7)-x(t))+x(P6)-x(t)" actx="27.41855485241411" y="y(t)+E1*cos(72)*(y(P8)-y(t))+E1*sin(72)*(y(P7)-y(t))+y(P6)-y(t)" acty="2.1245963621205894" fixed="true">Point à &quot;x(P76)+E1*cos(72)*(x(P13)-x(P76))+E1*sin(72)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(72)*(y(P13)-y(P76))+E1*sin(72)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P14" n="15" color="1" hidden="super" showname="true" x="x(t)+E1*cos(144)*(x(P8)-x(t))+E1*sin(144)*(x(P7)-x(t))+x(P6)-x(t)" actx="30.78065706845419" y="y(t)+E1*cos(144)*(y(P8)-y(t))+E1*sin(144)*(y(P7)-y(t))+y(P6)-y(t)" acty="2.5620806371493687" fixed="true">Point à &quot;x(P76)+E1*cos(144)*(x(P13)-x(P76))+E1*sin(144)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(144)*(y(P13)-y(P76))+E1*sin(144)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P15" n="16" color="1" hidden="super" showname="true" x="x(t)+E1*cos(216)*(x(P8)-x(t))+E1*sin(216)*(x(P7)-x(t))+x(P6)-x(t)" actx="30.126721950410783" y="y(t)+E1*cos(216)*(y(P8)-y(t))+E1*sin(216)*(y(P7)-y(t))+y(P6)-y(t)" acty="3.4831563745142002" fixed="true">Point à &quot;x(P76)+E1*cos(216)*(x(P13)-x(P76))+E1*sin(216)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(216)*(y(P13)-y(P76))+E1*sin(216)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P16" n="17" color="1" hidden="super" showname="true" x="x(t)+E1*cos(288)*(x(P8)-x(t))+E1*sin(288)*(x(P7)-x(t))+x(P6)-x(t)" actx="26.3604656049827" y="y(t)+E1*cos(288)*(y(P8)-y(t))+E1*sin(288)*(y(P7)-y(t))+y(P6)-y(t)" acty="3.614928211389758" fixed="true">Point à &quot;x(P76)+E1*cos(288)*(x(P13)-x(P76))+E1*sin(288)*(x(P14)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*cos(288)*(y(P13)-y(P76))+E1*sin(288)*(y(P14)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P17" n="18" color="1" hidden="super" showname="true" x="x(t)+E1*(x(P8)-x(t))+x(P6)-x(t)" actx="24.68672629120659" y="y(t)+E1*(y(P8)-y(t))+y(P6)-y(t)" acty="2.7752919479740283" fixed="true">Point à &quot;x(P76)+E1*(x(P13)-x(P76))+x(P15)-x(P76)&quot;, &quot;y(P76)+E1*(y(P13)-y(P76))+y(P15)-y(P76)&quot; </Point>
<Point name="P18" n="19" color="2" hidden="super" showname="true" xcoffset="-0.5082592121982206" ycoffset="0.1820221839562297" keepclose="true" bold="true" large="true" x="x(t)+E1*(x(P6)-x(t))" actx="27.874625153493675" y="y(t)+E1*(y(P6)-y(t))" acty="4.110163869721103" fixed="true">Point à &quot;x(P76)+E1*(x(P15)-x(P76))&quot;, &quot;y(P76)+E1*(y(P15)-y(P76))&quot; </Point>
<Midpoint name="M1" n="20" color="1" type="thick" hidden="super" showname="true" xcoffset="-0.3659466327827179" ycoffset="-0.20665765098231326" keepclose="true" bold="true" large="true" first="P18" second="P6" shape="circle">Milieu de P18 et P6</Midpoint>
<Point name="P19" n="21" color="5" type="thick" target="true" bold="true" x="x(P12)+x(t)-x(M1)" actx="26.938823088123698" y="y(P12)+y(t)-y(M1)" acty="-1.1299440489076482" shape="circle" fixed="true">Point à &quot;x(P19)+x(P76)-x(M2)&quot;, &quot;y(P19)+y(P76)-y(M2)&quot; </Point>
<Point name="P20" n="22" color="5" type="thick" target="true" bold="true" x="x(P9)+x(t)-x(M1)" actx="27.592758206167105" y="y(P9)+y(t)-y(M1)" acty="-2.0510197862724797" shape="circle" fixed="true">Point à &quot;x(P16)+x(P76)-x(M2)&quot;, &quot;y(P16)+y(P76)-y(M2)&quot; </Point>
<Point name="P21" n="23" color="5" type="thick" target="true" bold="true" x="x(P10)+x(t)-x(M1)" actx="29.67065164933122" y="y(P10)+y(t)-y(M1)" acty="-1.780639634761087" shape="circle" fixed="true">Point à &quot;x(P17)+x(P76)-x(M2)&quot;, &quot;y(P17)+y(P76)-y(M2)&quot; </Point>
<Point name="P22" n="24" color="5" type="thick" target="true" bold="true" x="x(P11)+x(t)-x(M1)" actx="29.266497519943215" y="y(P11)+y(t)-y(M1)" acty="-1.21138352285675" shape="circle" fixed="true">Point à &quot;x(P18)+x(P76)-x(M2)&quot;, &quot;y(P18)+y(P76)-y(M2)&quot; </Point>
<Point name="P23" n="25" color="3" type="thick" target="true" bold="true" x="x(P17)+x(t)-x(M1)" actx="24.68672629120659" y="y(P17)+y(t)-y(M1)" acty="0.23756282481800595" shape="diamond" fixed="true">Point à &quot;x(P24)+x(P76)-x(M2)&quot;, &quot;y(P24)+y(P76)-y(M2)&quot; </Point>
<Point name="P24" n="26" color="3" type="thick" target="true" bold="true" x="x(P13)+x(t)-x(M1)" actx="27.41855485241411" y="y(P13)+y(t)-y(M1)" acty="-0.41313276103543295" shape="diamond" fixed="true">Point à &quot;x(P20)+x(P76)-x(M2)&quot;, &quot;y(P20)+y(P76)-y(M2)&quot; </Point>
<Point name="P25" n="27" color="3" type="thick" target="true" bold="true" x="x(P14)+x(t)-x(M1)" actx="30.78065706845419" y="y(P14)+y(t)-y(M1)" acty="0.02435151399334634" shape="diamond" fixed="true">Point à &quot;x(P21)+x(P76)-x(M2)&quot;, &quot;y(P21)+y(P76)-y(M2)&quot; </Point>
<Point name="P26" n="28" color="3" type="thick" target="true" bold="true" x="x(P15)+x(t)-x(M1)" actx="30.126721950410783" y="y(P15)+y(t)-y(M1)" acty="0.9454272513581778" shape="diamond" fixed="true">Point à &quot;x(P22)+x(P76)-x(M2)&quot;, &quot;y(P22)+y(P76)-y(M2)&quot; </Point>
<Point name="P27" n="29" color="3" type="thick" target="true" bold="true" x="x(P16)+x(t)-x(M1)" actx="26.3604656049827" y="y(P16)+y(t)-y(M1)" acty="1.0771990882337357" shape="diamond" fixed="true">Point à &quot;x(P23)+x(P76)-x(M2)&quot;, &quot;y(P23)+y(P76)-y(M2)&quot; </Point>
<Point name="P28" n="30" color="5" type="thick" target="true" bold="true" x="x(P8)+x(t)-x(M1)" actx="25.904395303903136" y="y(P8)+y(t)-y(M1)" acty="-1.6488677978855288" shape="circle" fixed="true">Point à &quot;x(P13)+x(P76)-x(M2)&quot;, &quot;y(P13)+y(P76)-y(M2)&quot; </Point>
<Point name="P29" n="31" color="1" target="true" bold="true" x="2*x(t)-x(P27)" actx="29.38878470200465" y="2*y(t)-y(P27)" acty="0.8695172418049117" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P34)&quot;, &quot;2*y(P76)-y(P34)&quot; </Point>
<Point name="P30" n="32" color="1" target="true" bold="true" x="2*x(t)-x(P23)" actx="31.06252401578076" y="2*y(t)-y(P23)" acty="1.7091535052206415" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P30)&quot;, &quot;2*y(P76)-y(P30)&quot; </Point>
<Point name="P31" n="33" color="1" target="true" bold="true" x="2*x(t)-x(P24)" actx="28.330695454573238" y="2*y(t)-y(P24)" acty="2.3598490910740804" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P31)&quot;, &quot;2*y(P76)-y(P31)&quot; </Point>
<Point name="P32" n="34" color="1" target="true" bold="true" x="2*x(t)-x(P25)" actx="24.96859323853316" y="2*y(t)-y(P25)" acty="1.922364816045301" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P32)&quot;, &quot;2*y(P76)-y(P32)&quot; </Point>
<Point name="P33" n="35" color="1" target="true" bold="true" x="2*x(t)-x(P26)" actx="25.622528356576566" y="2*y(t)-y(P26)" acty="1.0012890786804696" shape="circle" fixed="true">Point à &quot;2*x(P76)-x(P33)&quot;, &quot;2*y(P76)-y(P33)&quot; </Point>
<Point name="P34" n="36" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P19)" actx="28.81042721886365" y="2*y(t)-y(P19)" acty="3.0766603789462956" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P26)&quot;, &quot;2*y(P76)-y(P26)&quot; </Point>
<Point name="P35" n="37" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P28)" actx="29.844855003084213" y="2*y(t)-y(P28)" acty="3.5955841279241763" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P35)&quot;, &quot;2*y(P76)-y(P35)&quot; </Point>
<Point name="P36" n="38" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P21)" actx="26.07859865765613" y="2*y(t)-y(P21)" acty="3.7273559647997345" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P28)&quot;, &quot;2*y(P76)-y(P28)&quot; </Point>
<Point name="P37" n="39" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P22)" actx="26.482752787044134" y="2*y(t)-y(P22)" acty="3.1580998528953974" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P29)&quot;, &quot;2*y(P76)-y(P29)&quot; </Point>
<Point name="P38" n="40" color="2" type="thick" target="true" bold="true" x="2*x(t)-x(P20)" actx="28.156492100820245" y="2*y(t)-y(P20)" acty="3.997736116311127" shape="dcross" fixed="true">Point à &quot;2*x(P76)-x(P27)&quot;, &quot;2*y(P76)-y(P27)&quot; </Point>
<Segment name="s1" n="41" color="1" target="true" ctag0="thin" cexpr0="((a(P28,P20,P21)&gt;180)&amp;&amp;(a(P21,P20,P24)&gt;180))" from="P20" to="P21">Segment de P20 à P21</Segment>
<Segment name="s2" n="42" color="1" target="true" ctag0="thin" cexpr0="((a(P20,P21,P22)&gt;180)&amp;&amp;(a(P22,P21,P25)&gt;180))" from="P21" to="P22">Segment de P21 à P22</Segment>
<Segment name="s3" n="43" color="1" target="true" ctag0="thin" cexpr0="((a(P21,P22,P19)&gt;180)&amp;&amp;(a(P19,P22,P26)&gt;180))" from="P22" to="P19">Segment de P22 à P19</Segment>
<Segment name="s4" n="44" color="1" target="true" ctag0="thin" cexpr0="((a(P22,P19,P28)&gt;180)&amp;&amp;(a(P28,P19,P27)&gt;180))" from="P19" to="P28">Segment de P19 à P28</Segment>
<Segment name="s5" n="45" color="1" target="true" ctag0="thin" cexpr0="((a(P19,P28,P20)&gt;180)&amp;&amp;(a(P20,P28,P23)&gt;180))" from="P28" to="P20">Segment de P28 à P20</Segment>
<Segment name="s6" n="46" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P36,P32)&gt;180)&amp;&amp;(a(P32,P36,P37)&gt;180))" from="P36" to="P32">Segment de P36 à P32</Segment>
<Segment name="s7" n="47" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P37,P33)&gt;180)&amp;&amp;(a(P33,P37,P34)&gt;180))" from="P37" to="P33">Segment de P37 à P33</Segment>
<Segment name="s8" n="48" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P34,P29)&gt;180)&amp;&amp;(a(P29,P34,P35)&gt;180))" from="P34" to="P29">Segment de P34 à P29</Segment>
<Segment name="s9" n="49" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P38,P31)&gt;180)&amp;&amp;(a(P31,P38,P36)&gt;180))" from="P38" to="P31">Segment de P38 à P31</Segment>
<Segment name="s10" n="50" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P32,P27)&gt;180)&amp;&amp;(a(P27,P32,P23)&gt;180))" from="P32" to="P27">Segment de P32 à P27</Segment>
<Segment name="s11" n="51" color="1" target="true" ctag0="thin" cexpr0="((a(P27,P32,P23)&gt;180)&amp;&amp;(a(P23,P32,P36)&gt;180))" from="P32" to="P23">Segment de P32 à P23</Segment>
<Segment name="s12" n="52" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P33,P23)&gt;180)&amp;&amp;(a(P23,P33,P24)&gt;180))" from="P33" to="P23">Segment de P33 à P23</Segment>
<Segment name="s13" n="53" color="1" target="true" ctag0="thin" cexpr0="((a(P34,P29,P24)&gt;180)&amp;&amp;(a(P24,P29,P25)&gt;180))" from="P29" to="P24">Segment de P29 à P24</Segment>
<Segment name="s14" n="54" color="1" target="true" ctag0="thin" cexpr0="((a(P24,P29,P25)&gt;180)&amp;&amp;(a(P25,P29,P34)&gt;180))" from="P29" to="P25">Segment de P29 à P25</Segment>
<Segment name="s15" n="55" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P30,P25)&gt;180)&amp;&amp;(a(P25,P30,P26)&gt;180))" from="P30" to="P25">Segment de P30 à P25</Segment>
<Segment name="s16" n="56" color="1" target="true" ctag0="thin" cexpr0="((a(P25,P30,P26)&gt;180)&amp;&amp;(a(P26,P30,P35)&gt;180))" from="P30" to="P26">Segment de P30 à P26</Segment>
<Segment name="s17" n="57" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P31,P26)&gt;180)&amp;&amp;(a(P26,P31,P27)&gt;180))" from="P31" to="P26">Segment de P31 à P26</Segment>
<Segment name="s18" n="58" color="1" target="true" ctag0="thin" cexpr0="((a(P26,P31,P27)&gt;180)&amp;&amp;(a(P27,P31,P38)&gt;180))" from="P31" to="P27">Segment de P31 à P27</Segment>
<Segment name="s19" n="59" color="1" target="true" ctag0="thin" cexpr0="((a(P26,P30,P35)&gt;180)&amp;&amp;(a(P35,P30,P25)&gt;180))" from="P30" to="P35">Segment de P30 à P35</Segment>
<Segment name="s20" n="60" color="1" target="true" ctag0="thin" cexpr0="((a(P36,P38,P35)&gt;180)&amp;&amp;(a(P35,P38,P31)&gt;180))" from="P38" to="P35">Segment de P38 à P35</Segment>
<Segment name="s21" n="61" color="1" target="true" ctag0="thin" cexpr0="((a(P38,P35,P34)&gt;180)&amp;&amp;(a(P34,P35,P30)&gt;180))" from="P35" to="P34">Segment de P35 à P34</Segment>
<Segment name="s22" n="62" color="1" target="true" ctag0="thin" cexpr0="((a(P35,P34,P37)&gt;180)&amp;&amp;(a(P37,P34,P29)&gt;180))" from="P34" to="P37">Segment de P34 à P37</Segment>
<Segment name="s23" n="63" color="1" target="true" ctag0="thin" cexpr0="((a(P34,P37,P36)&gt;180)&amp;&amp;(a(P36,P37,P33)&gt;180))" from="P37" to="P36">Segment de P37 à P36</Segment>
<Segment name="s24" n="64" color="1" target="true" ctag0="thin" cexpr0="((a(P37,P36,P38)&gt;180)&amp;&amp;(a(P38,P36,P32)&gt;180))" from="P36" to="P38">Segment de P36 à P38</Segment>
<Segment name="s25" n="65" color="1" target="true" ctag0="thin" cexpr0="((a(P33,P24,P20)&gt;180)&amp;&amp;(a(P20,P24,P29)&gt;180))" from="P24" to="P20">Segment de P24 à P20</Segment>
<Segment name="s26" n="66" color="1" target="true" ctag0="thin" cexpr0="((a(P29,P25,P21)&gt;180)&amp;&amp;(a(P21,P25,P30)&gt;180))" from="P25" to="P21">Segment de P25 à P21</Segment>
<Segment name="s27" n="67" color="1" target="true" ctag0="thin" cexpr0="((a(P30,P26,P22)&gt;180)&amp;&amp;(a(P22,P26,P31)&gt;180))" from="P26" to="P22">Segment de P26 à P22</Segment>
<Segment name="s28" n="68" color="1" target="true" ctag0="thin" cexpr0="((a(P31,P27,P19)&gt;180)&amp;&amp;(a(P19,P27,P32)&gt;180))" from="P27" to="P19">Segment de P27 à P19</Segment>
<Segment name="s29" n="69" color="1" target="true" ctag0="thin" cexpr0="((a(P32,P23,P28)&gt;180)&amp;&amp;(a(P28,P23,P33)&gt;180))" from="P23" to="P28">Segment de P23 à P28</Segment>
<Segment name="s30" n="70" color="1" target="true" ctag0="thin" cexpr0="((a(P23,P33,P24)&gt;180)&amp;&amp;(a(P24,P33,P37)&gt;180))" from="P33" to="P24">Segment de P33 à P24</Segment>
</Objects>
</Macro>
<Macro Name="@builtin@/3Dtetra" showduplicates="true">
<Parameter name="O">=O</Parameter>
<Parameter name="X">=X</Parameter>
<Parameter name="Y">=Y</Parameter>
<Parameter name="Z">=Z</Parameter>
<Parameter name="G">G</Parameter>
<Objects>
<Point name="G" n="0" color="2" showname="true" xcoffset="0.2733753263073275" ycoffset="0.08411548501764265" keepclose="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(G)-windowcx)+windowcx+d(windowcx)" actx="3.243678253571642" y="(windoww/(windoww-d(windoww)))*(y(G)-windowcy)+windowcy+d(windowcy)" acty="0.7617185353379072" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(G)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(G)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="1" mainparameter="true" x="-1.656048748705789" y="0.6145164365570406">Point à -1.65605, 0.61452</Point>
<Expression name="E1" n="2" color="1" showname="true" showvalue="true" x="x(G)" y="y(G)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 3.24368, 1.07715</Expression>
<Point name="X" n="3" mainparameter="true" x="-2.2623898699385574" y="0.38163166366691936">Point à -2.26239, 0.38163</Point>
<Point name="Y" n="4" mainparameter="true" x="-0.8608440928453058" y="0.4369425085479858">Point à -0.86084, 0.43694</Point>
<Point name="Z" n="5" mainparameter="true" x="-1.656048748705789" y="1.5706713312510705">Point à -1.65605, 1.57067</Point>
<Point name="P6" n="6" color="2" showname="true" xcoffset="0.04205774250882044" ycoffset="0.25589304175426086" keepclose="true" target="true" bold="true" x="x(G)+(sqrt(3)/sqrt(2))*E1*(x(Z)-x(O))" actx="3.243678253571642" y="y(G)+(sqrt(3)/sqrt(2))*E1*(y(Z)-y(O))" acty="3.103810142402863" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Point name="P7" n="7" color="2" hidden="super" showname="true" x="x(G)" actx="3.243678253571642" y="y(G)-(y(P6)-y(G))/3" acty="-0.018978667017077955" shape="circle" fixed="true">Point à &quot;x(G)&quot;, &quot;y(G)-(y(P6)-y(G))/3&quot;</Point>
<Point name="P8" n="8" color="2" hidden="super" showname="true" x="x(P7)+E1*(x(X)-x(O))/sqrt(3)" actx="2.5435358344426855" y="y(P7)+E1*(y(X)-y(O))/sqrt(3)" acty="-0.28789083965363066" shape="circle" fixed="true">Point à &quot;x(P7)+E1*(x(X)-x(O))/sqrt(3)&quot;, &quot;y(P7)+E1*(y(X)-y(O))/sqrt(3)&quot;</Point>
<Point name="P9" n="9" color="2" target="true" x="x(P7)+2*(x(P7)-x(P8))" actx="4.643963091829555" y="y(P7)+2*(y(P7)-y(P8))" acty="0.5188456782560275" shape="circle" fixed="true">Point à &quot;x(P7)+2*(x(P7)-x(P8))&quot;, &quot;y(P7)+2*(y(P7)-y(P8))&quot;</Point>
<Point name="P10" n="10" color="2" target="true" bold="true" x="x(P8)+E1*(x(Y)-x(O))" actx="4.1339451461636525" y="y(P8)+E1*(y(Y)-y(O))" acty="-0.6430386956717402" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Point name="P11" n="11" color="2" target="true" bold="true" x="x(P8)+E1*(x(O)-x(Y))" actx="0.9531265227217189" y="y(P8)+E1*(y(O)-y(Y))" acty="0.06725701636447895" shape="circle" fixed="true">Point à &quot;x(C)+x(B)-x(A)&quot;, &quot;y(C)+y(B)-y(A)&quot; </Point>
<Segment name="s1" n="12" color="2" target="true" ctag0="thin" cexpr0="((a(P9,P6,P11)&gt;180)&amp;&amp;(a(P11,P6,P10)&gt;180))" from="P6" to="P11">Segment de P6 à P11</Segment>
<Segment name="s2" n="13" color="2" target="true" ctag0="thin" cexpr0="((a(P11,P6,P10)&gt;180)&amp;&amp;(a(P10,P6,P9)&gt;180))" from="P6" to="P10">Segment de P6 à P10</Segment>
<Segment name="s3" n="14" color="2" target="true" ctag0="thin" cexpr0="((a(P10,P6,P9)&gt;180)&amp;&amp;(a(P9,P6,P11)&gt;180))" from="P6" to="P9">Segment de P6 à P9</Segment>
<Segment name="s4" n="15" color="2" xcoffset="0.857441230031186" ycoffset="0.09819214404524133" keepclose="true" target="true" ctag0="thin" cexpr0="((a(P10,P9,P11)&gt;180)&amp;&amp;(a(P11,P9,P6)&gt;180))" from="P9" to="P11">Segment de P9 à P11</Segment>
<Segment name="s5" n="16" color="2" xcoffset="0.7535763009733026" ycoffset="0.8705187569218046" keepclose="true" target="true" ctag0="thin" cexpr0="((a(P11,P10,P9)&gt;180)&amp;&amp;(a(P9,P10,P6)&gt;180))" from="P10" to="P9">Segment de P10 à P9</Segment>
<Segment name="s6" n="17" color="2" target="true" ctag0="thin" cexpr0="((a(P9,P11,P10)&gt;180)&amp;&amp;(a(P10,P11,P6)&gt;180))" from="P11" to="P10">Segment de P11 à P10</Segment>
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
<Parameter name="o">o</Parameter>
<Objects>
<Point name="o" n="0" type="thick" showname="true" mainparameter="true" target="true" x="(windoww/(windoww-d(windoww)))*(x(o)-windowcx)+windowcx+d(windowcx)" actx="82.56393431232262" y="(windoww/(windoww-d(windoww)))*(y(o)-windowcy)+windowcy+d(windowcy)" acty="23.724926967364365" shape="circle" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(o)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(o)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Point name="O" n="1" mainparameter="true" x="74.67689368762788" y="21.581489236136537">Point à 74.67689, 21.58149</Point>
<Expression name="E1" n="2" color="2" showname="true" showvalue="true" x="x(o)" y="y(o)+15/pixel" value="2" prompt="k" fixed="true">Expression &quot;2&quot; à 82.56393, 23.87493</Expression>
<Point name="X" n="3" mainparameter="true" x="74.93019145339206" y="21.394237681230273">Point à 74.93019, 21.39424</Point>
<Point name="Y" n="4" mainparameter="true" x="75.6442820490727" y="21.630518563327104">Point à 75.64428, 21.63052</Point>
<Point name="Z" n="5" mainparameter="true" x="74.67689368762788" y="22.562576887807086">Point à 74.67689, 22.56258</Point>
<Point name="P6" n="6" showname="true" target="true" x="x(o)+E1*(-x(X)+x(Y)-x(Z)+x(O))" actx="83.9921155036839" y="y(o)+E1*(-y(X)+y(Y)-y(Z)+y(O))" acty="22.23531342821693" shape="circle" fixed="true">Point à &quot;x(o)+E1*(-x(X)+x(Y)-x(Z)+x(O))&quot;, &quot;y(o)+E1*(-y(X)+y(Y)-y(Z)+y(O))&quot;</Point>
<Point name="P7" n="7" showname="true" target="true" x="x(o)+E1*(-x(X)+x(Y)+x(Z)-x(O))" actx="83.9921155036839" y="y(o)+E1*(-y(X)+y(Y)+y(Z)-y(O))" acty="26.159664034899127" shape="circle" fixed="true">Point à &quot;x(o)+E1*(-x(X)+x(Y)+x(Z)-x(O))&quot;, &quot;y(o)+E1*(-y(X)+y(Y)+y(Z)-y(O))&quot;</Point>
<Point name="P8" n="8" target="true" x="x(o)+E1*(x(X)+x(Y)-x(Z)-x(O))" actx="85.00530656674057" y="y(o)+E1*(y(X)+y(Y)-y(Z)-y(O))" acty="21.486307208591878" shape="circle" fixed="true">Point à &quot;x(o)+E1*(x(X)+x(Y)-x(Z)-x(O))&quot;, &quot;y(o)+E1*(y(X)+y(Y)-y(Z)-y(O))&quot;</Point>
<Point name="P9" n="9" showname="true" target="true" x="x(o)+E1*(x(X)+x(Y)+x(Z)-3*x(O))" actx="85.00530656674057" y="y(o)+E1*(y(X)+y(Y)+y(Z)-3*y(O))" acty="25.41065781527406" shape="circle" fixed="true">Point à &quot;x(o)+E1*(x(X)+x(Y)+x(Z)-3*x(O))&quot;, &quot;y(o)+E1*(y(X)+y(Y)+y(Z)-3*y(O))&quot;</Point>
<Point name="P10" n="10" showname="true" target="true" x="2*x(o)-x(P9)" actx="80.12256205790466" y="2*y(o)-y(P9)" acty="22.03919611945467" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P9)&quot;, &quot;2*y(o)-y(P9)&quot;</Point>
<Point name="P11" n="11" showname="true" target="true" x="2*x(o)-x(P8)" actx="80.12256205790466" y="2*y(o)-y(P8)" acty="25.96354672613685" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P8)&quot;, &quot;2*y(o)-y(P8)&quot;</Point>
<Point name="P12" n="12" target="true" x="2*x(o)-x(P7)" actx="81.13575312096133" y="2*y(o)-y(P7)" acty="21.290189899829603" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P7)&quot;, &quot;2*y(o)-y(P7)&quot;</Point>
<Point name="P13" n="13" target="true" x="2*x(o)-x(P6)" actx="81.13575312096133" y="2*y(o)-y(P6)" acty="25.2145405065118" shape="circle" fixed="true">Point à &quot;2*x(o)-x(P6)&quot;, &quot;2*y(o)-y(P6)&quot;</Point>
<Segment name="s1" n="14" color="1" target="true" ctag0="thin" cexpr0="((a(P9,P7,P6)&gt;180)&amp;&amp;(a(P6,P7,P11)&gt;180))" from="P7" to="P6">Segment de P7 à P6</Segment>
<Segment name="s2" n="15" color="1" target="true" ctag0="thin" cexpr0="((a(P7,P11,P10)&gt;180)&amp;&amp;(a(P10,P11,P13)&gt;180))" from="P11" to="P10">Segment de P11 à P10</Segment>
<Segment name="s3" n="16" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P13,P12)&gt;180)&amp;&amp;(a(P12,P13,P9)&gt;180))" from="P13" to="P12">Segment de P13 à P12</Segment>
<Segment name="s4" n="17" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P9,P8)&gt;180)&amp;&amp;(a(P8,P9,P7)&gt;180))" from="P9" to="P8">Segment de P9 à P8</Segment>
<Segment name="s5" n="18" color="1" target="true" ctag0="thin" cexpr0="((a(P10,P11,P13)&gt;180)&amp;&amp;(a(P13,P11,P7)&gt;180))" from="P11" to="P13">Segment de P11 à P13</Segment>
<Segment name="s6" n="19" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P7,P9)&gt;180)&amp;&amp;(a(P9,P7,P6)&gt;180))" from="P7" to="P9">Segment de P7 à P9</Segment>
<Segment name="s7" n="20" color="1" target="true" ctag0="thin" cexpr0="((a(P12,P13,P9)&gt;180)&amp;&amp;(a(P9,P13,P11)&gt;180))" from="P13" to="P9">Segment de P13 à P9</Segment>
<Segment name="s8" n="21" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P11,P7)&gt;180)&amp;&amp;(a(P7,P11,P10)&gt;180))" from="P11" to="P7">Segment de P11 à P7</Segment>
<Segment name="s9" n="22" color="1" target="true" ctag0="thin" cexpr0="((a(P9,P8,P12)&gt;180)&amp;&amp;(a(P12,P8,P6)&gt;180))" from="P8" to="P12">Segment de P8 à P12</Segment>
<Segment name="s10" n="23" color="1" target="true" ctag0="thin" cexpr0="((a(P7,P6,P8)&gt;180)&amp;&amp;(a(P8,P6,P10)&gt;180))" from="P6" to="P8">Segment de P6 à P8</Segment>
<Segment name="s11" n="24" color="1" target="true" ctag0="thin" cexpr0="((a(P11,P10,P6)&gt;180)&amp;&amp;(a(P6,P10,P12)&gt;180))" from="P10" to="P6">Segment de P10 à P6</Segment>
<Segment name="s12" n="25" color="1" target="true" ctag0="thin" cexpr0="((a(P13,P12,P10)&gt;180)&amp;&amp;(a(P10,P12,P8)&gt;180))" from="P12" to="P10">Segment de P12 à P10</Segment>
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
<Point name="p" n="1" color="1" mainparameter="true" x="(windoww/(windoww-d(windoww)))*(x(p)-windowcx)+windowcx+d(windowcx)" actx="-2.5312001282524608" y="(windoww/(windoww-d(windoww)))*(y(p)-windowcy)+windowcy+d(windowcy)" acty="1.4860679450064422" fixed="true">Point à &quot;(windoww/(windoww-d(windoww)))*(x(p)-windowcx)+windowcx+d(windowcx)&quot;, &quot;(windoww/(windoww-d(windoww)))*(y(p)-windowcy)+windowcy+d(windowcy)&quot;</Point>
<Expression name="E1" n="2" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)+25/pixel" value="1" prompt="x" fixed="true">Expression &quot;0&quot; à -6.68004, 2.71076 </Expression>
<Expression name="E2" n="3" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)-4/pixel" value="2" prompt="y" fixed="true">Expression &quot;0&quot; à -5.68004, 2.71076 </Expression>
<Expression name="E3" n="4" color="1" type="thick" showname="true" showvalue="true" x="x(p)+7/pixel" y="y(p)-30/pixel" value="-1" prompt="z" fixed="true">Expression &quot;0&quot; à -4.58004, 2.71076 </Expression>
<Point name="P3" n="5" color="1" hidden="super" x="x(p)" actx="-2.5312001282524608" y="y(p)+40/pixel" acty="1.8860679450064421" fixed="true">Point à &quot;x(p)&quot;, &quot;y(p)+40/pixel&quot;</Point>
<Point name="P4" n="6" color="1" hidden="super" x="x(p)" actx="-2.5312001282524608" y="y(p)-35/pixel" acty="1.1360679450064421" fixed="true">Point à &quot;x(p)&quot;, &quot;y(p)-35/pixel&quot;</Point>
<Segment name="s1" n="7" color="1" target="true" from="P3" to="P4">Segment de P3 à P4</Segment>
<Point name="Z" n="8" mainparameter="true" x="1.015777063361877" y="1.4596355468668833">Point à 1.01578, 1.45964</Point>
<Point name="Y" n="9" mainparameter="true" x="2.0062468607753905" y="0.4624709308305989">Point à 2.00625, 0.46247</Point>
<Point name="X" n="10" mainparameter="true" x="0.7853269639841833" y="0.363843803570412">Point à 0.78533, 0.36384</Point>
<Point name="P8" n="11" color="1" target="true" bold="true" large="true" x="x(O)+E1*(x(X)-x(O))+E2*(x(Y)-x(O))+E3*(x(Z)-x(O))" actx="2.7427132604020112" y="y(O)+E1*(y(X)-y(O))+E2*(y(Y)-y(O))+E3*(y(Z)-y(O))" acty="-0.6355911938647798" fixed="true">Point à &quot;x(O)+E1*(x(X)-x(O))+E2*(x(Y)-x(O))+E3*(x(Z)-x(O))&quot;, &quot;y(O)+E1*(y(X)-y(O))+E2*(y(Y)-y(O))+E3*(y(Z)-y(O))&quot; </Point>
</Objects>
<PromptFor object0="E1" prompt0="x" object1="E2" prompt1="y" object2="E3" prompt2="z"/>
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
