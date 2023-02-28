
/spark/bin/spark-submit \
    --class part6practical.TestDeployApp \
    --master spark://(dockerID):7077 \
    --deploy-mode client \
    --verbose \
    --supervise \
    spark-essentials.jar /opt/spark-data/movies.json /opt/spark-data/goodMovies


/usr/bin/spark-submit \
--master yarn \
--class TestAppMain \
--num-executors 3 \
--driver-memory 2g \
--executor-memory 2g \
--executor-cores 1  \
--conf "spark.dynamicAllocation.enabled=false" \
--deploy-mode client \
--verbose \
s3://test-today-28/deploy/amr-spark-application.jar s3://test-today-28/source/movies.json s3://test-today-28/target/