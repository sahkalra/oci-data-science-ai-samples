package com.company;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.ailanguage.AIServiceLanguageClient;
import com.oracle.bmc.ailanguage.model.*;
import com.oracle.bmc.ailanguage.requests.*;
import com.oracle.bmc.ailanguage.responses.*;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static AIServiceLanguageClient client;
    private static String text = "My name is Sahil. My bank account is 90342050505. My credit card number is 4444222233339999";

    public static void main(String[] args) {
        try {
            ConfigFileAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider("DEFAULT");
//            AIServiceLanguageClient client = new AIServiceLanguageClient(provider);
            client = AIServiceLanguageClient.builder().endpoint("https://language.aiservice.us-ashburn-1.oci.oraclecloud.com").build(provider);

            Main aiServiceLanguageExample = new Main();

            BatchLanguageTranslationDetails batchLanguageTranslationDetails = BatchLanguageTranslationDetails.builder()
                    .targetLanguageCode("en")
                    .documents(new ArrayList<>(Arrays.asList(TextDocument.builder()
                            .key("key1")
                            .text("你")
                            .languageCode("zh").build()))).build();

            BatchLanguageTranslationRequest batchLanguageTranslationRequest = BatchLanguageTranslationRequest.builder()
                    .batchLanguageTranslationDetails(batchLanguageTranslationDetails).build();

            BatchLanguageTranslationResponse response1 = client.batchLanguageTranslation(batchLanguageTranslationRequest);
            System.out.println(response1);
            System.out.println("Translation: " + response1.getBatchLanguageTranslationResult().getDocuments().get(0).getTranslatedText());

//            DetectDominantLanguageResult dominantLanguageResult = aiServiceLanguageExample.getDominantLanguage(text);
//            aiServiceLanguageExample.printLanguageType(dominantLanguageResult);
//
//            DetectLanguageEntitiesResult entitiesResult = aiServiceLanguageExample.getLanguageEntities(text);
//            aiServiceLanguageExample.printEntities(entitiesResult);

//            BatchDetectLanguageEntitiesResult batchEntitiesResult = aiServiceLanguageExample.getLanguageBatchEntities(text);
//            System.out.println(batchEntitiesResult.getDocuments().toString());
//
//            Project languageProject = aiServiceLanguageExample.createLanguageProject();
//            System.out.println(languageProject.toString());
//
//            BatchDetectLanguagePiiEntitiesResult result = aiServiceLanguageExample.getPersonalIdentificationInformation(text,"en");
//            System.out.println("response" + result);

            client.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

//    private DetectDominantLanguageResult getDominantLanguage(String text) {
//        DetectDominantLanguageDetails languageDetails = DetectDominantLanguageDetails.builder().text(text).build();
//        DetectDominantLanguageRequest request = DetectDominantLanguageRequest.builder().detectDominantLanguageDetails(languageDetails).build();
//        DetectDominantLanguageResponse response = client.detectDominantLanguage(request);
//        return response.getDetectDominantLanguageResult();
//    }

//    private void printLanguageType(DetectDominantLanguageResult result) {
//        System.out.println("========= Dominant Language ========");
//        List<DetectedLanguage> languages = result.getLanguages();
//        List<String> languagesStr = languages.stream().map(language -> language.getName()+ " ("+language.getScore()+")").collect(Collectors.toList());
//        System.out.println(String.join(",", languagesStr));
//        System.out.println("========= End ========");
//        System.out.println();
//    }
//
//    private DetectLanguageEntitiesResult getLanguageEntities(String text) {
//        DetectLanguageEntitiesDetails entitiesDetails = DetectLanguageEntitiesDetails.builder().text(text).build();
//        DetectLanguageEntitiesRequest request = DetectLanguageEntitiesRequest.builder().detectLanguageEntitiesDetails(entitiesDetails).build();
//        DetectLanguageEntitiesResponse response = client.detectLanguageEntities(request);
//        return response.getDetectLanguageEntitiesResult();
//    }
//
//    private void printEntities(DetectLanguageEntitiesResult result) {
//        List<Entity> entities = result.getEntities();
//        String printFormat = "%s [%s]";
//        System.out.println("========= Entities ========");
//        entities.forEach(entity -> System.out.println(String.format(printFormat, entity.getText(), entity.getType())));
//        System.out.println("========= End ========");
//        System.out.println();
//    }

//    private BatchDetectLanguageEntitiesResult getLanguageBatchEntities(String text) {
//        TextDocument entityDocument = TextDocument.builder().key("doc1").text(text).languageCode("en").build();
//        java.util.List<TextDocument> documents = Arrays.asList(entityDocument);
//        BatchDetectLanguageEntitiesDetails entitiesDetails = BatchDetectLanguageEntitiesDetails.builder().documents(documents).build();
//        BatchDetectLanguageEntitiesRequest request = BatchDetectLanguageEntitiesRequest.builder().batchDetectLanguageEntitiesDetails(entitiesDetails).build();
//        BatchDetectLanguageEntitiesResponse response = client.batchDetectLanguageEntities(request);
//        return response.getBatchDetectLanguageEntitiesResult();
//    }

//    private BatchDetectLanguageEntitiesResult getLanguageBatchEntities(String text) {
//        EntityDocument entityDocument = EntityDocument.builder().key("doc1").text(text).languageCode("en").build();
//        java.util.List<EntityDocument> documents = Arrays.asList(entityDocument);
//        BatchDetectLanguageEntitiesDetails entitiesDetails = BatchDetectLanguageEntitiesDetails.builder().documents(documents).build();
//        BatchDetectLanguageEntitiesRequest request = BatchDetectLanguageEntitiesRequest.builder().batchDetectLanguageEntitiesDetails(entitiesDetails).build();
//        BatchDetectLanguageEntitiesResponse response = client.batchDetectLanguageEntities(request);
//        return response.getBatchDetectLanguageEntitiesResult();
//    }

//     Create AiLanguageProject
    private Project createLanguageProject() {
        String compartmentId = "ocid1.tenancy.oc1..aaaaaaaaih4krf4od5g2ym7pffbp6feof3rx64522aoxxvv3iuw3tam6fvea";
        CreateProjectDetails projectDetails = CreateProjectDetails.builder().compartmentId(compartmentId).build();
        CreateProjectRequest request = CreateProjectRequest.builder().createProjectDetails(projectDetails).build() ;
        CreateProjectResponse response = client.createProject(request);
        return response.getProject();
    }

    private BatchDetectLanguagePiiEntitiesResult getPersonalIdentificationInformation(String text, String languageCode) throws IOException {
        TextDocument textDocument = TextDocument.builder()
                .key("new")
                .text(text)
                .languageCode(languageCode)
                .build();

        // Masking mode is MASK
        PiiEntityMasking piiEntityMasking = PiiEntityMask
                .builder()
                .maskingCharacter("#")
                .isUnmaskedFromEnd(false)
                .leaveCharactersUnmasked(4)
                .build();

        //Masking mode is REPLACE
        PiiEntityMasking piiEntityReplace = PiiEntityReplace
                .builder()
                .replaceWith("Entity")
                .build();

        //Masking mode is REMOVE
        PiiEntityMasking piiEntityRemove = PiiEntityRemove.builder().build();

        Map<String, PiiEntityMasking> masking = new HashMap<>();
        masking.put("ALL", piiEntityMasking);

        BatchDetectLanguagePiiEntitiesDetails batchDetectLanguagePiiEntitiesDetails = BatchDetectLanguagePiiEntitiesDetails
                .builder()
                .compartmentId("ocid1.tenancy.oc1..aaaaaaaaih4krf4od5g2ym7pffbp6feof3rx64522aoxxvv3iuw3tam6fvea")
                .documents(Arrays.asList(textDocument))
                .masking(masking)
                .build();

        BatchDetectLanguagePiiEntitiesRequest batchDetectLanguagePiiEntitiesRequest = BatchDetectLanguagePiiEntitiesRequest
                .builder()
                .batchDetectLanguagePiiEntitiesDetails(batchDetectLanguagePiiEntitiesDetails)
                .build();

        BatchDetectLanguagePiiEntitiesResponse batchDetectLanguagePiiEntitiesResponse =
                client.batchDetectLanguagePiiEntities(batchDetectLanguagePiiEntitiesRequest);

        System.out.println(batchDetectLanguagePiiEntitiesRequest.toString());
        return batchDetectLanguagePiiEntitiesResponse.getBatchDetectLanguagePiiEntitiesResult();
    }
}
