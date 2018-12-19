

##  org.apache.spark:spark-core_2.11 vulnerability found in scala-examples/pom.xml

https://nvd.nist.gov/vuln/detail/CVE-2018-17190

CVE-2018-17190 More information
low severity
Vulnerable versions: >= 0
Patched version: No fix
In all versions of Apache Spark, its standalone resource manager accepts code to execute on a 'master' host, that then runs that code on 'worker' hosts. The master itself does not, by design, execute user code. A specially-crafted request to the master can, however, cause the master to execute code too. Note that this does not affect standalone clusters with authentication enabled. While the master host typically has less outbound access to other resources than a worker, the execution of code on the master is nevertheless unexpected.

Mitigation
Enable authentication on any Spark standalone cluster that is not otherwise secured from unwanted access, for example by network-level restrictions. Use spark.authenticate and related security properties described at https://spark.apache.org/docs/latest/security.html
