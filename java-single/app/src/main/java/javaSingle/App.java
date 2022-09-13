package javaSingle;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.netty.buffer.Unpooled;
import javaSingle.gql.meters.Meters;
import javaSingle.klv.commands.MeterDisplayBlockUpdate;

public class App {
    public void makeMeterBlocks() throws IOException {
        int[] data = {13, 0, 0, 40, 252, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 49, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 128, 11, 129, 11, 130, 11, 131, 11, 132, 11, 133, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 2, 28, 1, 2, 0, 6, 0, 0, 0, 77, 97, 105, 110, 32, 50, 1, 4, 0, 0, 0, 76, 105, 110, 101, 1, 0, 0, 0, 0, 0, 6, 0, 0, 0, 134, 11, 135, 11, 136, 11, 137, 11, 138, 11, 139, 11, 0, 0, 0, 0, 6, 0, 0, 0, 1, 0, 0, 0, 76, 1, 0, 0, 0, 82, 1, 0, 0, 0, 67, 1, 0, 0, 0, 101, 1, 0, 0, 0, 108, 1, 0, 0, 0, 114, 8, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 255, 0, 0, 1, 0, 3, 0, 0, 0, 0, 0, 128, 2, 0, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for(int i=0; i < data.length; ++i){
            dos.writeByte(data[i]);
        }

        var out = Unpooled.buffer().order(LITTLE_ENDIAN) .writeBytes(baos.toByteArray());

        var header = out.readUnsignedInt();

        var klv_type = (header << 0) >> 27;
        var version = (header << 5) >> 22;
        var id = (header << 10) >> 10;
        var len = out.readUnsignedInt();

        var meterDisplayBlockUpdate = new MeterDisplayBlockUpdate(out);
        var meters = Meters.from(meterDisplayBlockUpdate);
    }

    public static void main(String[] args) throws IOException {
        var start = System.nanoTime();
        var records = new ArrayList<Record>();
        var numRuns = 100000;
        var app = new App();

        for (int i = 0; i < numRuns; i++) {
            var runStart = System.nanoTime();
            app.makeMeterBlocks();
            records.add(new Record(i, System.nanoTime() - runStart));
        }

        var end = System.nanoTime();
        var diff = end - start;

        System.out.println("Elapsed :: "+ (diff / 1000));
        System.out.println("Runs    :: "+ numRuns);
        System.out.println("Avg     :: "+ (records.stream().mapToInt(r -> (int) r.duration).sum() / records.size()) / 1000);
    }

    public static class Record {
        public Integer index;
        public long duration;

        public Record(Integer index, long duration) {
            this.index = index;
            this.duration = duration;
        }
    }
}
