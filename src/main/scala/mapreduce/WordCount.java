package mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WordCount {
    public WordCount() {
    }

    public static void main(String[] var0) throws Exception {
        Configuration var1 = new Configuration();
        Job var2 = Job.getInstance(var1, "word count");
        var2.setJarByClass(WordCount.class);
        var2.setMapperClass(WordCount.TokenizerMapper.class);
        var2.setCombinerClass(WordCount.IntSumReducer.class);
        var2.setReducerClass(WordCount.IntSumReducer.class);
        var2.setOutputKeyClass(Text.class);
        var2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(var2, new Path(var0[0]));
        FileOutputFormat.setOutputPath(var2, new Path(var0[1]));
        System.exit(var2.waitForCompletion(true) ? 0 : 1);
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public IntSumReducer() {
        }

        public void reduce(Text var1, Iterable<IntWritable> var2, Reducer<Text, IntWritable, Text, IntWritable>.Context var3) throws IOException, InterruptedException {
            int var4 = 0;

            IntWritable var6;
            for(Iterator var5 = var2.iterator(); var5.hasNext(); var4 += var6.get()) {
                var6 = (IntWritable)var5.next();
            }

            this.result.set(var4);
            var3.write(var1, this.result);
        }
    }

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static final IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public TokenizerMapper() {
        }

        public void map(Object var1, Text var2, Mapper<Object, Text, Text, IntWritable>.Context var3) throws IOException, InterruptedException {
            StringTokenizer var4 = new StringTokenizer(var2.toString());

            while(var4.hasMoreTokens()) {
                this.word.set(var4.nextToken());
                var3.write(this.word, one);
            }

        }
    }
}
