package in.myinnos.bondcoin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class BondCoinActivity extends AppCompatActivity {

    private TextView textView;
    private String OUTPUT = "";
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        textView.setText("Initializing ..\n\n it may take some time");
        System.out.println("Trying to Mine block 1... ");
        OUTPUT = OUTPUT + "Trying to Mine block 1...";
        addBlock(new Block("Hi im the first block", "0"));

        System.out.println("Trying to Mine block 2... ");
        OUTPUT = OUTPUT + "Trying to Mine block 2...";
        addBlock(new Block("Yo im the second block", blockchain.get(blockchain.size() - 1).hash));

        System.out.println("Trying to Mine block 3... ");
        OUTPUT = OUTPUT + "Trying to Mine block 3...";
        addBlock(new Block("Hey im the third block", blockchain.get(blockchain.size() - 1).hash));

        OUTPUT = OUTPUT + "\nBlockchain is Valid: " + isChainValid();
        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = StringUtil.getJson(blockchain);
        OUTPUT = OUTPUT + "\nThe block chain: ";
        System.out.println("\nThe block chain: ");
        OUTPUT = OUTPUT + blockchainJson;
        System.out.println(blockchainJson);

        textView.setText(OUTPUT);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }

        }
        return true;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
}