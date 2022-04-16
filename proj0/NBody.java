public class NBody {
    public static double readRadius(String txtName)
    {
        In in =new In(txtName);
        int PlanetNum=in.readInt();
        double PlanetRadius =in.readDouble();
        return PlanetRadius;
    }
    public static Planet[] readPlanets(String txtName)
    {
        In in =new In(txtName);
        int PlanetNum=in.readInt();
        double PlanetRadius =in.readDouble();
        double[] PlanetxxPos=new double[PlanetNum];
        double[] PlanetyyPos=new double[PlanetNum];
        double[] PlanetxxV=new double[PlanetNum];
        double[] PlanetyyV=new double[PlanetNum];
        double[] Planetm=new double[PlanetNum];
        String[] Planetimg=new String[PlanetNum];
        Planet[] ReadPlanetsArray=new Planet[PlanetNum];
        for (int i=0;i<PlanetNum;i++)
        {
            PlanetxxPos[i]=in.readDouble();
            PlanetyyPos[i]=in.readDouble();
            PlanetxxV[i]=in.readDouble();
            PlanetyyV[i]=in.readDouble();
            Planetm[i]=in.readDouble();;
            Planetimg[i]=in.readString();
            ReadPlanetsArray[i]= new Planet(PlanetxxPos[i],PlanetyyPos[i],PlanetxxV[i],PlanetyyV[i],Planetm[i],Planetimg[i]);
        }
        return ReadPlanetsArray;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename= args[2];
        double Radius=readRadius(filename);
        Planet[] PlanetsInfo= readPlanets(filename);
        String imageToDraw = "images/starfield.jpg";
        StdDraw.setScale(-Radius, Radius);
        StdDraw.clear();
        StdDraw.picture(0,0,imageToDraw);
        for (Planet s : PlanetsInfo)
        {
            s.draw();
        }
        StdDraw.show();
        StdDraw.pause(2000);
        StdDraw.enableDoubleBuffering();
        double time =0;
        double[] xForce=new double[PlanetsInfo.length];
        double[] yForce=new double[PlanetsInfo.length];
        while (time<T)
        {
            for(int i=0;i<PlanetsInfo.length;i++)
            {
                xForce[i]=PlanetsInfo[i].calcNetForceExertedByX(PlanetsInfo);
                yForce[i]=PlanetsInfo[i].calcNetForceExertedByY(PlanetsInfo);
            }
            for(int i=0;i<PlanetsInfo.length;i++)
            {
                PlanetsInfo[i].update(dt,xForce[i],yForce[i]);
            }
            StdDraw.setScale(-Radius, Radius);
            StdDraw.clear();
            StdDraw.picture(0,0,imageToDraw);
            for (Planet s : PlanetsInfo)
            {
                s.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time+=dt;
        }
        StdOut.printf("%d\n", PlanetsInfo.length);
        StdOut.printf("%.2e\n", Radius);
        for (int i = 0; i < PlanetsInfo.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    PlanetsInfo[i].xxPos, PlanetsInfo[i].yyPos, PlanetsInfo[i].xxVel,
                    PlanetsInfo[i].yyVel, PlanetsInfo[i].mass, PlanetsInfo[i].imgFileName);
        }
    }
}
