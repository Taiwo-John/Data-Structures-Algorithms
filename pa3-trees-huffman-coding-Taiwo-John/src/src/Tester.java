public class Tester {

    public static void main(String[] args) {
        String test = HuffEncoder.readfile("C:\\Users\\ALU_Student\\IdeaProjects\\pa3-trees-huffman-coding-Taiwo-John\\src\\src\\text_input.txt");
        HuffEncoder.writer("C:\\Users\\ALU_Student\\IdeaProjects\\pa3-trees-huffman-coding-Taiwo-John\\src\\src\\TaiwoJohn_TextInput.txt",test);
        final HuffEncoder compresser = new HuffEncoder();
        final HuffEncoder.EncodedResult result = compresser.encode(test);
        String Encoded_message = result.compressed_data;

        HuffEncoder.writer("C:\\Users\\ALU_Student\\IdeaProjects\\pa3-trees-huffman-coding-Taiwo-John\\src\\src\\TaiwoJohn_EncodedMessage.txt", Encoded_message);
        System.out.println("The encoded bit is: \n" + Encoded_message);
        System.out.println("The decoded message is:\n" + compresser.decode(result));
    }
}