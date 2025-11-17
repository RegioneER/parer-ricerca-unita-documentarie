## Container scan evidence CVE
<strong>Image name:</strong> registry.ente.regione.emr.it/parer/okd/ricerca-unita-documentarie:sast
<br/><strong>Run date:</strong> Mon Nov 17 10:42:06 CET 2025
<br/><strong>Produced by:</strong> <a href="https://gitlab.ente.regione.emr.it/parer/okd/ricerca-unita-documentarie/-/jobs/814152">Job</a>
<br/><strong>CVE founded:</strong> 2
| CVE | Description | Severity | Solution | 
|:---:|:---|:---:|:---|
| [CVE-2025-59375](http://www.openwall.com/lists/oss-security/2025/09/16/2)|libexpat in Expat before 2.7.2 allows attackers to trigger large dynamic memory allocations via a small document that is submitted for parsing.|High|No solution provided|
| [CVE-2025-12863](https://access.redhat.com/security/cve/CVE-2025-12863)|A flaw was found in the xmlSetTreeDoc() function of the libxml2 XML parsing library. This function is responsible for updating document pointers when XML nodes are moved between documents. Due to improper handling of namespace references, a namespace pointer may remain linked to a freed memory region when the original document is destroyed. As a result, subsequent operations that access the namespace can lead to a use-after-free condition, causing an application crash.|High|No solution provided|
