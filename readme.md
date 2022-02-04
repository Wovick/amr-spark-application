
/spark/bin/spark-submit \
    --class part6practical.TestDeployApp \
    --master spark://(dockerID):7077 \
    --deploy-mode client \
    --verbose \
    --supervise \
    spark-essentials.jar /opt/spark-data/movies.json /opt/spark-data/goodMovies

aws s3 cp s3://lux-big/amr-spark-application.jar .

/usr/bin/spark-submit --class part6practical.TestDeployApp --supervise --verbose amr-spark-application.jar movies.json goodMovies


/usr/bin/spark-submit --class TestAppMain --supervise --verbose s3a://lux-big/amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies


/usr/bin/spark-submit \
--master yarn \
--class TestAppMain \
--deploy-mode cluster \
--verbose \
amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies



--conf spark.sql.shuffle.partitions=
--num-executors $NUM_EXECUTORS \
--executor-cores $EXECUTOR_CORES \
--executor-memory $EXECUTOR_MEMORY \


export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
./bin/spark-submit \
--master local[2] \
--class org.apache.spark.examples.SparkPi \
s3a://your_bucket/.../spark-examples_2.11-2.4.6-SNAPSHOT.jar


/usr/bin/spark-submit \
--master yarn \
--class TestAppMain \
--deploy-mode client \
--verbose \
amr-spark-application.jar s3://lux-big/movies.json s3://lux-big/good_movies