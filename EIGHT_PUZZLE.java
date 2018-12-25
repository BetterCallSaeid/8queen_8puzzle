
import java.util.Random;
import java.util.Stack;

public class EIGHT_PUZZLE {

    public static void main(String[] args){
        EIGHT_PUZZLE eight_Puzzle = new EIGHT_PUZZLE();     
        eight_Puzzle.Init();  
    }
    int[][] goalState = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    int[][] gemeBoard = {
        {2, 6, 3},
        {1, 0, 4},
        {8, 7, 5}
    };
    int emptyTileRow = 0;
    int emptyTileColumn = 0;
    int stepCounter = 0;

    int fMin;
    Node fMinNode;

    Random random = new Random();
    Stack<Node> stack_state = new Stack<>();//for backtracking

    public void Init() {

        getEmptyTile();
        fMin = getF(gemeBoard);

        System.out.println("Start: \n");

        try {
            HillClimbing();
        } catch (Exception e) {
            System.out.println("Goal can not Found" +e);
            printResults(fMinNode.state, "Found: Min F " + fMin);
        }
    }

    public void HillClimbing() throws Exception {

        while (true) {
            System.out.println("/nSteps: " + (++stepCounter));

            Node lowestPossible_fn_node = getLowestFNode();
            addState(Sequence.neighbors_nodeArray);

            printResults(lowestPossible_fn_node.state, "-------new state");
            int fnCounter = 1;
            for (int i = 1; i < Sequence.neighbors_nodeArray.length; i++) {
                if (Sequence.neighbors_nodeArray[i - 1].fn == Sequence.neighbors_nodeArray[i].fn) {//fns are equal
                    fnCounter++;
                }
            }
            if (Sequence.neighbors_nodeArray.length != 1 && fnCounter == Sequence.neighbors_nodeArray.length) {//all fns are equal, equal chances to choose
                System.out.println("---fs are equal local maxima---");

                for (int i = 0; i < Sequence.neighbors_nodeArray.length; i++) {
                    if (stack_state != null) {
                        System.out.println("pop " + (i + 1));
                        stack_state.pop();
                    } else {
                        System.out.println("empty stack inside loop");
                    }
                }

                if (stack_state != null) {
                    Node gameEightPuzzleNode = stack_state.pop();
                    gemeBoard = gameEightPuzzleNode.state;
                    Sequence.preState = gameEightPuzzleNode.parent;
                    getEmptyTile();

                    printResults(gemeBoard, "popped state from all equal fn");
                    System.out.println("empty tile position: " + emptyTileRow + ", " + emptyTileColumn);
                } else {
                    System.out.println("stack empty inside first lm check");
                }
            } else {

                System.out.println("lowest fn: " + lowestPossible_fn_node.fn);
                if (lowestPossible_fn_node.fn == 0) {
                    System.out.println("Complete");
                    System.out.println("Total steps: " + stepCounter);
                    break;
                }

                if (lowestPossible_fn_node.fn <= fMin) {
                    fMin = lowestPossible_fn_node.fn;
                    fMinNode = lowestPossible_fn_node;

                    if (stack_state != null) {
                        Node gameEightPuzzleNode = stack_state.pop();
                        gemeBoard = gameEightPuzzleNode.state;
                        Sequence.preState = gameEightPuzzleNode.parent;
                        getEmptyTile();

                        printResults(gemeBoard, "-------new state as going deeper");
                        System.out.println("empty tile position: " + emptyTileRow + ", " + emptyTileColumn);
                    } else {
                        System.out.println("stack empty");
                    }

                } else {
                    System.out.println("local maxima");
                    System.out.println("getting higher, not possible");
                
                    for (int i = 0; i < Sequence.neighbors_nodeArray.length; i++) {
                        if (stack_state != null) {
                            stack_state.pop();
                        } else {
                            System.out.println("empty stack inside loop");
                        }

                    }
                    if (stack_state != null) {
                        Node gameEightPuzzleNode = stack_state.pop();
                        gemeBoard = gameEightPuzzleNode.state;
                        Sequence.preState = gameEightPuzzleNode.parent;
                        getEmptyTile();

                        printResults(gemeBoard, "popped state from getting higher");
                        System.out.println("empty tile position: " + emptyTileRow + ", " + emptyTileColumn);
                    } else {
                        System.out.println("stack empty inside second lm check");
                    }
                }
            }
        }
    }

    private Node getLowestFNode() {

        if (emptyTileRow == 0 && emptyTileColumn == 0) {
            Node fn_array[] = {bottomF(), rightF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 0 && emptyTileColumn == 1) {
            Node fn_array[] = {leftF(), bottomF(), rightF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 0 && emptyTileColumn == 2) {
            Node fn_array[] = {leftF(), bottomF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 1 && emptyTileColumn == 0) {
            Node fn_array[] = {bottomF(), rightF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 1 && emptyTileColumn == 1) {
            Node fn_array[] = {leftF(), bottomF(), rightF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 1 && emptyTileColumn == 2) {
            Node fn_array[] = {leftF(), bottomF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 2 && emptyTileColumn == 0) {
            Node fn_array[] = {rightF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 2 && emptyTileColumn == 1) {
            Node fn_array[] = {leftF(), rightF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileRow == 2 && emptyTileColumn == 2) {
            Node fn_array[] = {leftF(), topF()};
            Node lowest_fn_node = Sequence.sort(fn_array);
            return lowest_fn_node;
        }
        return null;
    }

    private Node leftF() {

        int left_state[][] = new int[gemeBoard.length][gemeBoard[0].length];
        for (int i = 0; i < gemeBoard.length; i++) {
            for (int j = 0; j < gemeBoard[0].length; j++) {

                if (i == emptyTileRow && j == emptyTileColumn) {
                    left_state[i][j] = gemeBoard[i][j - 1];
                    left_state[i][j - 1] = gemeBoard[i][j];
                } else {
                    left_state[i][j] = gemeBoard[i][j];
                }
            }
        }
        printResults(left_state, "left state");
        Node node = new Node(getF(left_state), left_state, gemeBoard);
        return node;
    }

    private Node rightF() {

        int right_state[][] = new int[gemeBoard.length][gemeBoard[0].length];
        for (int i = 0; i < gemeBoard.length; i++) {
            for (int j = 0; j < gemeBoard[0].length; j++) {

                if (i == emptyTileRow && j == emptyTileColumn) {
                    right_state[i][j] = gemeBoard[i][j + 1];
                    right_state[i][j + 1] = gemeBoard[i][j];
                    j++;
                } else {
                    right_state[i][j] = gemeBoard[i][j];
                }
            }
        }

        printResults(right_state, "right state");
        Node node = new Node(getF(right_state), right_state, gemeBoard);
        return node;
    }

    private Node topF() {

        int up_state[][] = new int[gemeBoard.length][gemeBoard[0].length];
        for (int i = 0; i < gemeBoard.length; i++) {
            for (int j = 0; j < gemeBoard[0].length; j++) {

                if (i == emptyTileRow && j == emptyTileColumn) {
                    up_state[i][j] = gemeBoard[i - 1][j];
                    up_state[i - 1][j] = gemeBoard[i][j];
                } else {
                    up_state[i][j] = gemeBoard[i][j];
                }
            }
        }
        printResults(up_state, "up state");
        Node node = new Node(getF(up_state), up_state, gemeBoard);
        return node;
    }

    private Node bottomF() {

        int down_state[][] = new int[gemeBoard.length][gemeBoard[0].length];
        for (int i = 0; i < gemeBoard.length; i++) {
            for (int j = 0; j < gemeBoard[0].length; j++) {

                if ((i - 1) == emptyTileRow && j == emptyTileColumn) {
                    down_state[i][j] = gemeBoard[i - 1][j];
                    down_state[i - 1][j] = gemeBoard[i][j];
                } else {
                    down_state[i][j] = gemeBoard[i][j];
                }
            }
        }
        printResults(down_state, "down state");
        Node node = new Node(getF(down_state), down_state, gemeBoard);
        return node;
    }

    private int getF(int[][] game_state) {

        int fn_count = 0;
        for (int i = 0; i < game_state.length; i++) {
            for (int j = 0; j < game_state[0].length; j++) {
                if (game_state[i][j] != goalState[i][j] && game_state[i][j] != 0) {//found misplaced tiles
                    fn_count++;
                }
            }
        }
        return fn_count;
    }

    private void addState(Node nodeArray[]) {
        for (int i = nodeArray.length - 1; i >= 0; i--) {
            stack_state.add(nodeArray[i]);
        }
    }

    private void getEmptyTile() {

        nestedloop:
        for (int i = 0; i < gemeBoard.length; i++) {
            for (int j = 0; j < gemeBoard[0].length; j++) {
                if (gemeBoard[i][j] == 0) {
                    emptyTileRow = i;
                    emptyTileColumn = j;
                    break nestedloop;
                }
            }
        }
    }

    private void printResults(int[][] state, String message) {
        System.out.println(message);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(state[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
    
}
