package com.udd.back.feature_docs.util;

import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory;
import ai.djl.inference.Predictor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VectorizationUtil {

    @Value("${vectorization.model}")
    private String djlModel;

    @Value("${vectorization.engine}")
    private String engine;

    private ZooModel<String, float[]> model;
    private static Predictor<String, float[]> predictor;

    @PostConstruct
    public void init() throws Exception {
        String djlPath = "djl://ai.djl.huggingface.pytorch/" + djlModel;

        Criteria<String, float[]> criteria =
                Criteria.builder()
                        .setTypes(String.class, float[].class)
                        .optModelUrls(djlPath)
                        .optEngine(engine)
                        .optTranslatorFactory(new TextEmbeddingTranslatorFactory())
                        .optProgress(new ProgressBar())
                        .build();

        model = criteria.loadModel();
        predictor = model.newPredictor();
    }

    public float[] getEmbedding(String text) throws TranslateException {
        return predictor.predict(text);
    }

    @PreDestroy
    public void close() {
        if (predictor != null) predictor.close();
        if (model != null) model.close();
    }

}
