/* 

Copyright 2006 Rene Grothmann, modified by Eric Hakenholz

This file is part of C.a.R. software.

C.a.R. is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

C.a.R. is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package rene.zirkel.objects;

// file: LineCircleIntersectionObject.java
import java.util.ArrayList;
import java.util.Collections;
import rene.util.xml.XmlWriter;
import rene.zirkel.construction.Construction;
import rene.zirkel.structures.Coordinates4;
import rene.zirkel.structures.CoordinatesXY;

public class QuadricQuadricIntersectionObject extends IntersectionObject {

    private int Rank; //numéro de l'intersection, à savoir 0,1,2 ou 3

    public QuadricQuadricIntersectionObject(final Construction c,
            final QuadricObject P1, final QuadricObject P2,
            final int rank) {
        super(c, P1, P2);
        Rank=rank;
        validate();
    }

    // public void updateCircleDep ()
    // { ((QuadricObject)P2).addDep(this);
    // ((PrimitiveLineObject)P1).addDep(this);
    // }
    @Override
    public void validate() {
        final boolean oldvalid=Valid;
        if (!P1.valid()||!P2.valid()) {
            Valid=false;
        } else {
            Valid=true;
        }
        if (!Valid) {
            return;
        }
        
        final ArrayList<CoordinatesXY> c=QuadricObject.intersect((QuadricObject) P1, (QuadricObject) P2);
        Collections.sort(c);
        
        if (Double.isNaN(c.get(Rank).X)) {
            if (oldvalid&&getConstruction().shouldSwitch()) {
                doSwitch();
                if (!getConstruction().noteSwitch()) {
                    Switched=false;
                }
            }
            ;
            Valid=false;
            return;
        }
        setXY(c.get(Rank).X, c.get(Rank).Y);
    }

    @Override
    public void printArgs(final XmlWriter xml) {
        super.printArgs(xml);
        xml.printArg("which", ""+Rank);
    }

    @Override
    public boolean isSwitchable() {
        return false;
    }

    @Override
    public boolean canAlternate() {
        return true;
    }
}
