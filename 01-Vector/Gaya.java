
/**
 * Write a description of class Gaya here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gaya
{
    private Vector3D titikAcuan;
    private Vector3D arah;

    /**
     * Constructor for objects of class Gaya
     */
    public Gaya(Vector3D titikAcuan, Vector3D arah)
    {
       this.titikAcuan=titikAcuan;
       this.arah=arah;
    }

    public Vector3D getTitikAcuan()
    {
        return titikAcuan;
    }
    
    public Vector3D getArah()
    {
        return arah;
    }
}
