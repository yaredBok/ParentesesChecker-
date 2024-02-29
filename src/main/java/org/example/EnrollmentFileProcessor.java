package org.example;

import java.io.*;
import java.util.*;

public class EnrollmentFileProcessor {
    public static void main(String[] args) {
        String csvFile = "enrollment_data.csv";
        String line ;
       String csvSplitBy =",";

        Map<String, List<Enrollee>> insuranceCompanyMap = new HashMap<>();


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                System.out.println(line);
                String userId = data[0];
                String firstName = data[1];
                String lastName = data[2];
                int version = Integer.parseInt(data[3].trim());
                String insuranceCompany = data[4];
                Enrollee enrollee = new Enrollee(userId, firstName, lastName, version, insuranceCompany);

                insuranceCompanyMap.computeIfAbsent(insuranceCompany, k -> new ArrayList<>()).add(enrollee);
            }

            for (Map.Entry<String,List<Enrollee>> entry : insuranceCompanyMap.entrySet()){
                String insuranceCompany = entry.getKey();
                List<Enrollee> enrollees = entry.getValue();

                //Sort enrollees by last name, first name and version
                enrollees.sort(Comparator.comparing(Enrollee::getFirstName)
                        .thenComparing(Enrollee::getFirstName));

        // Remove duplicates and keep only the record with the highest version

            Map<String,Enrollee>uniqueEnrollees = new HashMap<>();
            for (Enrollee enrollee : enrollees){
                String userId = enrollee.getUserId();
                if (!uniqueEnrollees.containsKey(userId) ||
                enrollee.getVersion() > uniqueEnrollees.get(userId).getVersion()){
                    uniqueEnrollees.put((userId),enrollee);
                }
            }

//Write sorted and unique enrollees to a new CSV file

        try (FileWriter writer = new FileWriter(insuranceCompany + "-enrollees.csv")) {

            for (Enrollee enrollee : uniqueEnrollees.values()) {
                writer.write(enrollee.toString() + "\n");
            }
        }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NumberFormatException e){
            System.err.println("Error parsing version: " + e.getMessage());

        }
        }
}

