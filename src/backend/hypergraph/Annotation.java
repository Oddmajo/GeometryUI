package backend.hypergraph;

public class Annotation
{
    // The string version of the reason that the edge was created.
    private String justification;    
    public String getJustification() { return justification; }

    // Has the user indicated that the use of this 
    public boolean active;
    public boolean IsActive() { return active; }

    // Disallow a generic edge annotation; force the use of parameters
    private Annotation()
    {
        justification = "";
        active = false;
    }

    public Annotation(String just, boolean active)
    {
        justification = just;
        this.active = active;
    }

    @Override
    public String toString()
    {
        return justification;
    }
}
