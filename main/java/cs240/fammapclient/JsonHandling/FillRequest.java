package cs240.fammapclient.JsonHandling;



public class FillRequest extends Request {

    /** Username for which generations will be generated */
    private String username;
    /** Number of generations which will be generated, default = 4 */
    private int generations;

    public String getUsername() {
        return username;
    }
    public int getGenerations() {
        return generations;
    }
    /** Constructor
     * @param username
     * @param generations
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }
}
