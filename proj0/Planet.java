public class Planet {
    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;
    public Planet (double xP,double yP,double xV,double yV,double m,String img)
    {
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }
    public Planet (Planet p)
    {
        xxPos=p.xxPos;
        yyPos=p.yyPos;
        xxVel=p.xxVel;
        yyVel=p.yyVel;
        mass=p.mass;
        imgFileName=p.imgFileName;
    }
    public double calcDistance (Planet p)
    {
        double TwoPlanetDistance=Math.sqrt((this.xxPos-p.xxPos)*(this.xxPos-p.xxPos)+(this.yyPos-p.yyPos)*(this.yyPos-p.yyPos));
        return TwoPlanetDistance;
    }
    public static double G=6.67e-11;
    public double calcForceExertedBy (Planet p)
    {
                double calcForceExertedBy=G*this.mass*p.mass/(this.calcDistance(p)*this.calcDistance(p));
        return calcForceExertedBy;
    }
    public double calcForceExertedByX (Planet p)
    {
        double calcForceExertedByX=this.calcForceExertedBy(p)*(p.xxPos-this.xxPos)/this.calcDistance(p);
        return calcForceExertedByX;
    }
    public double calcForceExertedByY (Planet p)
    {
        double calcForceExertedByY=this.calcForceExertedBy(p)*(p.yyPos-this.yyPos)/this.calcDistance(p);
        return calcForceExertedByY;
    }
    public double calcNetForceExertedByX (Planet[] p)
    {
        double calcNetForceExertedByX=0;
        for (int i=0;i<p.length;i++)
        {
            if (this.equals(p[i]))
                continue;
            else
                calcNetForceExertedByX+=this.calcForceExertedByX(p[i]);
        }
        return calcNetForceExertedByX;
    }
    public double calcNetForceExertedByY (Planet[] p)
    {
        double calcNetForceExertedByY=0;
        for (int i=0;i<p.length;i++)
        {
            if (this.equals(p[i]))
                continue;
            else
            calcNetForceExertedByY+=this.calcForceExertedByY(p[i]);
        }
        return calcNetForceExertedByY;
    }
    public void update(double dt, double fX, double fY)
    {
        double PaX=fX/this.mass;
        double PaY=fY/this.mass;
        this.xxVel+=PaX*dt;
        this.yyVel+=PaY*dt;
        this.xxPos+=this.xxVel*dt;
        this.yyPos+=this.yyVel*dt;
    }
    public void draw()
    {
        StdDraw.picture(xxPos,yyPos,imgFileName);
    }
}