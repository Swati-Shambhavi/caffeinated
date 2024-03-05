package com.caffeinated.productcraftsmanservice.util;

import com.caffeinated.productcraftsmanservice.dto.CustomizedProductResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import static net.logstash.logback.argument.StructuredArguments.kv;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class AiResponseMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static CustomizedProductResponse mapToCustomizedProductResponse(String rawResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(rawResponse);
            JsonNode contentNode = rootNode.path("candidates").path(0).path("content");

            String productName = extractField(contentNode, "Product Name");
            String productDescription = extractField(contentNode, "Product Description");
            String imageUrl = extractField(contentNode, "Image URL");
            List<String> ingredients = extractIngredients(contentNode);
            String caffeineContent = extractField(contentNode, "Caffeine Content");
            List<String> recipe = extractRecipe(contentNode);

            CustomizedProductResponse response = new CustomizedProductResponse();
            response.setProductName(removeExtraCharacters(productName));
            response.setProductDescription(removeExtraCharacters(productDescription));
            response.setImageUrl(imageUrl);
            response.setIngredients(removeExtraChars(ingredients));
            response.setCaffeineContent(caffeineContent);
            response.setRecipeSteps(recipe);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new CustomizedProductResponse();
        }
    }

    private static List<String> removeExtraChars(List<String> ingredients) {
        return ingredients.stream().map(AiResponseMapper::removeExtraCharacters).collect(Collectors.toList());
    }

    private static String removeExtraCharacters(String data) {
        return data.replaceAll("^\\*+|\\*+$", "");
    }

    private static String extractField(JsonNode contentNode, String field) {
        JsonNode partsNode = contentNode.path("parts");

        for (JsonNode part : partsNode) {
            String text = part.path("text").asText();
            Pattern pattern = Pattern.compile(field + ":\\s*([^\\n]*)");
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        }

        return null;
    }

    private static List<String> extractIngredients(JsonNode contentNode) {
        List<String> ingredients = new ArrayList<>();
        JsonNode partsNode = contentNode.path("parts");
        log.info("****************0. outside loop {}",kv("partsNode",partsNode));
        String text="";
        if(!partsNode.isEmpty()){
            text = partsNode.get(0).path("text").asText();
        }
        log.info("****************1. outside loop {}",kv("text",text));
        String[] lines = text.split("\\n");
        StringBuilder ingredientsBuilder = new StringBuilder();
        boolean foundIngredients = false;
        for (String line : lines) {
            if (foundIngredients && !line.contains("Caffeine Content:")) {
                ingredientsBuilder.append(line).append("\n");
            }

            if (line.contains("Ingredients:")) {
                foundIngredients = true;
                // Extract the content starting from "Ingredients:" and stop at "Caffeine Content:"
                int startIndex = line.indexOf("Ingredients:") + "Ingredients:".length();
                int endIndex = line.indexOf("Caffeine Content:");
                if (startIndex >= 0 && endIndex >= 0) {
                    String ingredientsText = line.substring(startIndex, endIndex);
                    ingredientsBuilder.append(ingredientsText).append("\n");
                }
            }
            if (line.contains("Caffeine Content:")) {
                break;
            }
        }
        ingredients = List.of(ingredientsBuilder.toString().trim().split("#"));
        ingredients=ingredients.stream().filter(ingredient-> ingredient.length()>0).map(String::trim).collect(Collectors.toList());
        return ingredients;
    }



    private static  List<String> extractRecipe(JsonNode contentNode) {
        List<String> recipeSteps = new ArrayList<>();
        JsonNode partsNode = contentNode.path("parts");
        log.info("****************0. outside loop {}",kv("partsNode",partsNode));
        String text="";
        if(!partsNode.isEmpty()){
            text = partsNode.get(0).path("text").asText();
        }
        log.info("****************1. outside loop {}",kv("text",text));
        String[] lines = text.split("\\n");
        StringBuilder recipeBuilder = new StringBuilder();
        boolean foundRecipe = false;
        for (String line : lines) {
            if (foundRecipe && !line.trim().isEmpty()) {
                if (line.trim().startsWith("|")) {
                    recipeBuilder.append(line.trim()).append("\n");
                }
            }
            if (line.contains("Recipe:")) {
                foundRecipe = true;
                int startIndex = line.indexOf("Recipe:") + "Recipe:".length();
                if (startIndex >= 0) {
                    String recipeText = line.substring(startIndex);
                    recipeBuilder.append(recipeText).append("\n");
                }
            }
        }
        recipeSteps = List.of(recipeBuilder.toString().trim().split("\\|"));
        recipeSteps=recipeSteps.stream().map(String::trim).filter(recipe-> recipe.length()>0).collect(Collectors.toList());
        return recipeSteps;
    }

}
