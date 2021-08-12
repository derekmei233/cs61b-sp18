public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G = 6.67 * Math.pow(10, -11);
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
         xxPos = xP;
         yyPos = yP;
         xxVel = xV;
         yyVel = yV;
         mass = m;
         imgFileName = img;
    }

    public Planet(Planet b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    public double calcDistance(Planet pairb){
        double result = Math.sqrt(Math.pow(this.xxPos - pairb.xxPos, 2)
         + Math.pow(this.yyPos - pairb.yyPos, 2));
        return result;
    }
    public double calcForceExertedBy(Planet pairb){
        double force = G * this.mass * pairb.mass /
         ( Math.pow(this.xxPos - pairb.xxPos, 2) + Math.pow(this.yyPos - pairb.yyPos, 2));
         return force;
    }
    public double calcForceExertedByX(Planet pairb){
        double force = this.calcForceExertedBy(pairb);
        double forcex = force * (pairb.xxPos - this.xxPos) / this.calcDistance(pairb);
        return forcex;
    }
    public double calcForceExertedByY(Planet pairb){
        double force = this.calcForceExertedBy(pairb);
        double forcey = force * (pairb.yyPos - this.yyPos) / this.calcDistance(pairb);
        return forcey;
    }
    public double calcNetForceExertedByX(Planet[] pairs){
        double netForceX = 0;
        for (int i =0; i < pairs.length; i ++){
            if (this.equals(pairs[i])){
                continue;
            }
            netForceX += this.calcForceExertedByX(pairs[i]);
        }
        return netForceX;
    }
    public double calcNetForceExertedByY(Planet[] pairs){
        double netForceY = 0;
        for (int i = 0; i < pairs.length; i ++){
            if (this.equals(pairs[i])){
                continue;
            }
            netForceY += this.calcForceExertedByY(pairs[i]);
        }
        return netForceY;
    }
    public void update(double dt, double fx, double fy){
        double ax = fx / this.mass;
        double ay = fy / this.mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }
    public void draw(){
        String targetfile = "./images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, targetfile);
    }
}