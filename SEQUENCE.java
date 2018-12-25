
import java.util.Arrays;

public class Sequence {
   static int[][] preState;
    static Node neighbors_nodeArray[];

    

    public static Node[] getParentRemovedEightPuzzleNodeArray(Node []nodeArray, int[][] preState) {
        Node[] parentRemovedEightPuzzleNodeArray = new Node[nodeArray.length - 1];
        int j = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (Arrays.deepEquals(nodeArray[i].state, preState)) {
            } else {
                parentRemovedEightPuzzleNodeArray[j] = nodeArray[i];
                j++;
            }
        }
        return parentRemovedEightPuzzleNodeArray;
    }
    public static Node sort(Node[] nodeArray) {
        
        if(preState!=null){
            nodeArray = getParentRemovedEightPuzzleNodeArray(nodeArray, preState);//remove parent
        }
        
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = nodeArray.length - 1; j > i; j--) {
                if (nodeArray[j].fn < nodeArray[j - 1].fn) {
                    Node temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j - 1];
                    nodeArray[j - 1] = temp;
                }
            }
        }
        Sequence.neighbors_nodeArray = nodeArray;
        return nodeArray[0];
    }
}

