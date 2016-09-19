package ske.aurora.openshift.referanse.test;

public class RequestKorrelasjon {
    public static final String KORRELASJONS_ID = "Korrelasjonsid";
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    private RequestKorrelasjon() {
    }

    public static String getId() {
        return THREAD_LOCAL.get();
    }

    public static void setId(String correlationId) {
        THREAD_LOCAL.set(correlationId);
    }

    public static void cleanup() {
        THREAD_LOCAL.remove();
    }
}