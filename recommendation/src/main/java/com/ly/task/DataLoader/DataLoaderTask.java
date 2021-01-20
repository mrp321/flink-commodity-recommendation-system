package com.ly.task.DataLoader;


import com.ly.entity.RatingEntity;
import com.ly.map.DataToHbaseMapFunction;
import com.ly.util.Property;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class DataLoaderTask {
    private static final String CLS_PTH = DataLoaderTask.class.getClassLoader().getResource("").getPath();

    public static void main(String[] args) throws Exception {

//        System.clearProperty("hadoop.home.dir");
//        System.setProperty("hadoop.home.dir", "D:/develop/hadoop-2.7.2");
//        System.setProperty("HADOOP_HOME", "D:/develop/hadoop-2.7.2");
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String jarFiles = "C:\\Users\\Lenovo\\IdeaProjects\\flink-commodity-recommendation-system\\recommendation\\target\\recommendation-1.0-SNAPSHOT.jar";
//        Configuration conf = new Configuration();
//        ExecutionEnvironment env = ExecutionEnvironment.createRemoteEnvironment("192.168.229.139", 6123, jarFiles);
        String productFilePath = Property.getStrValue("product.data.path");
        String ratingFilePath = Property.getStrValue("rating.data.path");

        if (System.getProperty("os.name").startsWith("Windows")) {
            productFilePath = CLS_PTH + productFilePath;
            ratingFilePath = CLS_PTH + ratingFilePath;
        }
        System.out.println("product.data.path: " + productFilePath);

        System.out.println("rating.data.path: " + ratingFilePath);
        /*
         * fieldDelimiter(",")表示分隔符
         * ignoreFirstLine()表示是否忽略第一行
         * includeFields(true,false,true)表示不需要第二列
         * pojoType(ProductEntity.class, "productId", "name", "imageUrl", "categories", "tags")ProductEntity，使用后面这5个属性
         * */
//        DataSet<ProductEntity> source= env.readCsvFile(productFilePath)
//                .fieldDelimiter("^")
//                .includeFields("1100111")
//                .pojoType(ProductEntity.class, "productId", "name", "imageUrl", "categories", "tags")
//                .map(new DataLoaderMapFunction());
        DataSet<RatingEntity> source2 = env.readCsvFile(ratingFilePath)
                .fieldDelimiter(",")
                .pojoType(RatingEntity.class, "userId", "productId", "score", "timestamp")
                .map(new DataToHbaseMapFunction());
//        source.print();
        source2.print();
        env.execute("Load Data");
    }


}
