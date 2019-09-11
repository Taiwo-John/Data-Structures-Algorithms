import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

//Start of the class.

public class HuffEncoder {

    // This variable is using ASCII character set for all our characters (Maximum size = 256).
    public static final int Character_Set = 256;


    //The encode method takes the parameter of the string to encode.
    public EncodedResult encode (final String data){

        // Then creates the frequency table from that string.
        final int [] frequency_tab = createFrequencyTable(data);

        //Generates a huffman tree from the given frequency table.
        final Node root = huffmantree(frequency_tab);
        final Map<Character, String> codeTable = createCodeTable(root);
        return new EncodedResult(getEncodedData(data, codeTable), root);
//        It returns the encoded data converted to string using the getEncodedData method.
    }

    // This method takes in the encoded data and the codeTable and returns a string of the encoded data.
    private static String getEncodedData(final String data, final Map<Character, String> codeTable){
        final StringBuilder resultbuild = new StringBuilder();
        for (final char char_: data.toCharArray()){
            resultbuild.append(codeTable.get(char_));
        }

        return resultbuild.toString();
    }

    // This method creates the code table, and maps it using a hash map.
    private static Map<Character, String> createCodeTable(final Node root){

        final Map<Character, String> codeTable = new HashMap<>();

        createCodeTablerec(root, "", codeTable);

        return codeTable;
    }

    // This is the recursive method of the code table.
    private static void createCodeTablerec(final Node node,
                                           final String text,
                                           final Map<Character, String> codeTable){
        if (!node.isleaf()){
            createCodeTablerec(node.left, text + '0', codeTable);
            createCodeTablerec(node.right, text + '1', codeTable);
        }

        else{
            codeTable.put(node.char_, text);
        }
    }

    // This method creates the huffman tree from the frequency table, using a priority queue and generates the left and right
    // children of the tree.
    private static Node huffmantree(int [] frequency){

        final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

        for (char i = 0; i < Character_Set; i++){
            if(frequency[i] > 0){
                priorityQueue.add(new Node (i, frequency[i], null, null));
            }
        }

        if (priorityQueue.size() == 1){
            priorityQueue.add(new Node('\0', 1, null, null));
        }

        while(priorityQueue.size() > 1){
            final Node left = priorityQueue.poll();
            final Node right = priorityQueue.poll();
            final Node parent = new  Node ('\0', left.frequency + right.frequency, left, right);
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }

    //This method creates the frequency table of the input string.

    public static int[] createFrequencyTable(final String data){
        final int[] frequency = new int [Character_Set];
        for (final char char_: data.toCharArray()){
            frequency[char_] ++;
        }
        return frequency;
    }

    // This is the decompression/decoding method. It converts the encoded message into its original format.
    public String decode(final EncodedResult encoded_data){

        final StringBuilder resultBuild = new StringBuilder();

        Node current_ = encoded_data.getRoot();
        int i =0;

        while(i < encoded_data.getCompressed_data().length()){
            while(!current_.isleaf()){
                char bin_bit = encoded_data.getCompressed_data().charAt(i);
                if (bin_bit == '1'){
                    current_ = current_.right;
                }
                else if(bin_bit == '0'){
                    current_ = current_.left;
                }
                else{
                    throw new IllegalArgumentException("Invalid bit value in String");
                }
                i++;
            }

            resultBuild.append(current_.char_);
            current_ = encoded_data.getRoot();
        }
        return resultBuild.toString();
    }


    // The comparator Node method that compares one node to another before placing them in the huffman tree.
    static class Node implements Comparable<Node>{
        private final char char_;
        private final int frequency;
        private final Node left;
        private final Node right;

        private Node(final char char_,
                     final int frequency,
                     final Node left, final Node right){
            this.char_ = char_;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        private boolean isleaf(){
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(Node other_node) {


            final int compare_frequencies = Integer.compare(this.frequency, other_node.frequency);

            if (compare_frequencies != 0){
                return compare_frequencies;
            }

            return Integer.compare(this.char_, other_node.char_);
        }
    }

    // The Encoded Result method. This is used to get the encoded tree.
    static class EncodedResult{
        final Node root;
        final String compressed_data;
        EncodedResult(final String compressed_data, final Node root){
            this.compressed_data = compressed_data;
            this.root = root;
        }

        public Node getRoot(){
            return this.root;
        }

        public String getCompressed_data(){
            return this.compressed_data;
        }

    }


    // This readfile method read the contents of a file and returns a string.
    public static String readfile(String filename){

        String text = "";

        try {
            FileReader file_ = new FileReader(filename);
            BufferedReader buffer_ = new BufferedReader(file_);

            String char_;

            while ((char_ = buffer_.readLine()) != null){

//                System.out.println(char_);
                text = text.concat("\n");
                text = text.concat(char_);
            }
//           System.out.println(text);
            buffer_.close();
        }
        catch (IOException e){
            System.out.println("File not found");
        }

        return text;
    }


    // This writer method is used to write to a file.
    public static void writer(String filename, String str){
       try{
           BufferedWriter writer_ = new BufferedWriter(new FileWriter(filename));
           writer_.write(str);
           writer_.close();
       }

       catch (IOException e){
           System.out.println("File not found");
       }

    }

}


/*
    Complexities

    - Encode Method: 0(nlogn)
    - Decode Method: 0(nlogn)
    - Frequency Table: 0(n)
    - Code Table: 0(1)

    References:

    - GeekforGeeks Huffman Coding Explanation
    - Software Architecture and Design Blog Huffman Coding Explanation
    - StackExchange
    - Stack Overflow.
 */