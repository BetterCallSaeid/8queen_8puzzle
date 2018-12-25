
class Node {

    int fn;
    int[][] state;
    int [][] parent;
    public Node(int fn, int[][] state, int[][]parent) {
        this.fn = fn;
        this.state = state;
        this.parent = parent;
    }
}
