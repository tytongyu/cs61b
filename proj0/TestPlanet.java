public class TestPlanet
{
    public static void main(String[] args) {
        double G=6.67e-11;
        double testAxP=0;
        double testAyP=0;
        double testAxV=0;
        double testAyV=0;
        double testAm=5;
        String testAimg="imgA";
        double testBxP=-3;
        double testByP=-4;
        double testBxV=0;
        double testByV=0;
        double testBm=5;
        String testBimg="imgA";
        Planet testA =new Planet(0,0,testAxV,testAyV,testAm,testAimg);
        Planet testB =new Planet(testBxP,testByP,testBxV,testByV,testBm,testBimg);
        String judgexxForce;
        String judgeyyForce;
        if (testA.calcForceExertedByX(testB)==-4.002e-11)
            judgexxForce="PASS";
        else
            judgexxForce="FAIL";
        if (testA.calcForceExertedByY(testB)==-5.336e-11)
            judgeyyForce="PASS";
        else
            judgeyyForce="FAIL";
        System.out.println(judgexxForce+": xxForce Planet(): Exceptes -4.002e-11 and you gave"+testA.calcForceExertedByX(testB));
        System.out.println(judgeyyForce+": yyForce Planet(): Exceptes -5.336e-11 and you gave"+testA.calcForceExertedByY(testB));
    }
}