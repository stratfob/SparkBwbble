
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.Arrays;

public final class Main {

    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder().appName("myapp").getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        JavaRDD<String> text = jsc.textFile("hdfs:///user/hdfs/data.txt");

        double start = System.currentTimeMillis();
        JavaPairRDD<String, Integer> counts = text
                .flatMap(s -> Arrays.asList(s.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b);
        counts.saveAsTextFile("hdfs:///user/hdfs/out");
        double end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end-start));
        spark.stop();
    }
}