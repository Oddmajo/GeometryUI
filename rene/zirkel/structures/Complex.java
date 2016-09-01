/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rene.zirkel.structures;

/**
 *
 * @author erichake
 */
public class Complex {
    private double Re,Im;

    public Complex(double a,double b){
        Re=a;
        Im=b;
    }
    public Complex(double a){
        Re=a;
        Im=0;
    }

    public double real(){
        return Re;
    }
    public double img(){
        return Im;
    }

    public double module(){
        return Math.sqrt(Re*Re+Im*Im);
    }

    public double argument(){
        double theta=Math.atan2(Im, Re);
//        if (theta<0){
            return theta;
//        } else {
//            return theta+Math.PI*2;
//        }
    }

    public Complex sqrt() {
        double r=Math.sqrt(module());
        double theta=argument()/2;
        return new Complex(r*Math.cos(theta),r*Math.sin(theta));
    }

    public Complex sqrt3() {
        double r=Math.pow(module(),1.0/3.0);
        double theta=argument()/3;
        return new Complex(r*Math.cos(theta),r*Math.sin(theta));
    }
    
//    public Complex pow(double n){
//
//        double r=Math.pow(module(), n);
//        double theta=argument()*n;
//        return new Complex(r*Math.cos(theta),r*Math.sin(theta));
//    }

    public static Complex minus(Complex z1,Complex z2){
        return (new Complex(z1.Re-z2.Re,z1.Im-z2.Im));
    }

    public static Complex plus(Complex... list){
        Complex result=new Complex(0,0);
        for (Complex elt : list) {
            result.Re+=elt.Re;
            result.Im+=elt.Im;
        }
        return result;
    }

    public static Complex div(Complex z1,Complex z2) {
        double md=z2.Re*z2.Re+z2.Im*z2.Im;
        return new Complex((z1.Re*z2.Re+z1.Im*z2.Im)/md,(z2.Re*z1.Im-z1.Re*z2.Im)/md);
    }
    
    public static Complex div(Complex z1,double d) {
        return new Complex(z1.Re/d,z1.Im/d);
    }

    public static Complex mult(Complex z1,double m){
        return (new Complex(z1.Re*m,z1.Im*m));
    }
    public static Complex mult(double m,Complex z1){
        return (mult(z1,m));
    }
    public static Complex mult(Complex z1,Complex z2){
        Complex result=new Complex(0,0);
        result.Re=z1.Re*z2.Re-z1.Im*z2.Im;
        result.Im=z1.Re*z2.Im+z2.Re*z1.Im;
        return result;
    }

    public static Complex mult(final Complex... list){
        Complex result=new Complex(1,0);
        for (Complex elt : list) {
            result=mult(result,elt);
        }
        return result;
    }

}
