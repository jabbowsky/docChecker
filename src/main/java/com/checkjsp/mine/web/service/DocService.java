package com.checkjsp.mine.web.service;

import com.checkjsp.mine.web.model.Document;
import com.checkjsp.mine.web.model.Skill;
import com.checkjsp.mine.web.model.SkillWord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocService {
    private static Map<Integer, Document> docs = new HashMap<>();
    private static int maxDocId = 123;

    static {
        docs.put(0, new Document(0, "admin", "Perm part1", "Development of different modules of Transactions Data Warehouse in Oracle Exadata database using Java/J2EE, Perl, Informatica. Designing of the Transaction Data Warehouse that is the ‘golden source’ of equities data. Designing and implementation of complex and multi-module reporting systems.  Designing database structure (logical and physical), data modeling of Data Warehouse in Oracle Exadata database for keeping the global equities data. Designing and implementation data marts for reporting system. Development migration UNIX Shell, SQL, Oracle PL\\SQL scripts to load data from current structure Informatica to new one and from different trading upstream and reference systems.  Development and implementation of Data Quality processes for checking and monitoring incoming data.  Participation in performance tuning on valuable application modules, and implementation of the interfaces for files loading. Development of Java/J2EE, Spring and Oracle SQL, PL\\SQL parts of Transactions Data Warehouse.  Creating Control-M jobs for scheduling and running all components of the Transactions Data Warehouse with different dependencies between them.  Providing estimates of implementation for different tasks and projects.  Defining build, continuous integration and deployment standards and ensuring adherence to them.  Creating knowledge base articles.  Working with end users to help investigate, fix and explain production issues and data breaks between downstream and upstream systems. L2 Supporting of different infrastructures and environments. Collaboration with teammates using source control system CVS, git. Development and implementation of various reports with markups: XML, JSON\n" +
                "Technologies Used: Oracle Exadata database, Perl, Oracle PL/SQL, UNIX Shell, XML, JSON, CVS, git, Java/J2EE, Spring, Informatica, ETL, Control-M, Oracle, SQL, Agile, TDD, Waterfall, Data Warehouse, Atlassian JIRA", new ArrayList<SkillWord>()));
        docs.put(1, new Document(1, "admin", "Perm part2", "Creating parts for Data Warehouse of Reporting system in Equities system on Oracle Exadata database. Analysis of business requirements and needs. Implementation of business logic for KWG Regulatory Reporting System using Java/J2EE. Creating conceptual, logical and physical data models as per business requirements.  Modelling and implementing ETL/Data Quality processes for different layers of Data Warehouse using Informatica, Oracle PL\\SQL or Oracle SQL. Development of new and supporting existing components of Regulatory Reporting System. Implementation of scripts on Perl, UNIX Shell and Oracle components of reporting framework. Whitening modules on Java/J2EE, Spring. Development of processes automatically running reports with Control-M system. Communicating with business customers and various IT teams. Improving development process using best practices of software development. Participating in development of approaches for improving of quality code. Participating in code review. Ensuring good testing coverage (Unit, integration tests, testing evidences artifacts). Preparing of documentation (design docs, release notes, guides, etc.).  Data Quality validation via Informatica tool and L2 Supporting infrastructure components of reporting systems. Whitening Oracle PL\\SQL packages, procedures and functional for database duties. Development of API to receive and send data in XML or JSON formats. Verification of development code base by reviewing source code in CVS, git.\n" +
                "Technologies Used:  Oracle Exadata database, Perl, Oracle PL/SQL, UNIX Shell, XML, JSON, CVS, git, Java/J2EE, Spring, Informatica, ETL, Control-M, Oracle, SQL, Agile, TDD, Waterfall, Data Warehouse, Eclipse, Maven, uDeploy, ALM, Atlassian JIRA", new ArrayList<SkillWord>()));
        docs.put(2, new Document(2, "admin", "Perm part3", "Design and implementation of ETL modules with SQL, Oracle PL/SQL for receiving and transferring data from existing Oracle data warehouse to frequency compatibilities calculator LS telcom. Creation and implementation of new data warehouse models to contain information about users for web portal according to requirements of security and performance in Oracle Exadata database. Participation in development of new frequency assignment systems. Gathering requirements from different departments and describing it as data flows. Design and implementation of system architecture model in Oracle database and implementation frameworks for testing with UNIX Shell, Perl scripts. Development auto tests for performance, coverage and end-to-end testing. Design and implementation of user interface on Groovy scripts technology and creating API for connect to another part of system. Building user forms and business logic with Java/J2EE, Spring, UNIX Shell, Perl, Maven on various IDE such as IntelliJ IDEA, PL/SQL etc. Perform probation of data quality and validation with Informatica. L2 Support and working with requests in Atlassian JIRA. Working with report systems with XML, JSON markup languages. Using shared code by source control systems: git and CVS. Implementation of unit tests according to best practices. \n" +
                "Technologies Used: Oracle Exadata database, Perl, Oracle PL/SQL, UNIX Shell, XML, JSON, CVS, git, Java/J2EE, Spring, Informatica, ETL, Oracle, SQL, TDD, Data Warehouse, SVN, Maven, Atlassian JIRA, Sybase PowerDesigner, Groovy, IntelliJ IDEA", new ArrayList<SkillWord>()));
    }

    public List<Document> retrieveDocs() {
        return (List<Document>) docs.values();
    }

    public List<Document> coloredDocs(String user, List<Skill> skills) {
        List<Document> coloredDocs = new ArrayList<>();
        for (Document doc : docs.values()) {
            if (doc.getUser().equalsIgnoreCase(user)) {
                String header = doc.getHeader();
                String text = doc.getText();
                Collections.sort(skills, new SortByLenght());
                for (Skill skill : skills) {
                    text = text.replaceAll("(" + skill.getName() + ")", "<div class=\"inline-skill skill_" + String.valueOf(skill.getId())
                            + " color_" + String.valueOf(skill.getId()) + "\">$1</div>");
                }
                coloredDocs.add(new Document(doc.getId(), user, header, text, doc.getSkills()));
            }
        }
        return coloredDocs;
    }

    public void addDocument(String user, String header, String text) {
        ++maxDocId;
        docs.put(maxDocId, new Document(maxDocId, user, header, text, new ArrayList<SkillWord>()));
    }

    public void deleteDocument(String user, int id) {
        Document curDocument = docs.get(id);
        if (curDocument.getUser().equalsIgnoreCase(user)) {
            docs.remove(id);
        }
        /*for (Iterator<Document> itrDocument = docs.values().iterator(); itrDocument.hasNext(); ) {
            Document curDocument = itrDocument.next();
            System.err.println("deleteDocument : curDocument: " + String.valueOf(curDocument.getId()));
            if (curDocument.getUser().equalsIgnoreCase(user) && curDocument.getId() == id ){
                itrDocument.remove();
            }
        }*/
    }

    public void parseDocumentSkills(int id, List<SkillWord> skills) {
        Document docForCheck = docs.get(id);
        String text = docForCheck.getText();
        List<SkillWord> docSkills = new ArrayList<>();
        for (SkillWord skill : skills) {
            if (text.indexOf(skill.getName()) > 0) {
                docSkills.add(skill);
            }
        }
        System.err.println("parseDocumentSkills : found " + docSkills.size() + "; checked " + skills.size());
        docForCheck.setSkills(docSkills);
    }

    private class SortByLenght implements Comparator<Skill> {
        public int compare(Skill a, Skill b) {
            return b.getName().length() - a.getName().length();
        }
    }

}
