package com.minhpvn.restaurantsapp.model.googleMapModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QueryAddressResponse {
    @SerializedName("predictions")
    private final List<Predictions> predictions;

    @SerializedName("status")
    private final String status;

    public QueryAddressResponse(List<Predictions> predictions, String status) {
        this.predictions = predictions;
        this.status = status;
    }

    public List<Predictions> getPredictions() {
        return predictions;
    }

    public String getStatus() {
        return status;
    }

    public static class Predictions {
        @SerializedName("description")
        private final String description;

        @SerializedName("id")
        private final String id;

        @SerializedName("matched_substrings")
        private final List<MatchedSubstrings> matchedSubstrings;

        @SerializedName("place_id")
        private final String placeId;

        @SerializedName("reference")
        private final String reference;

        @SerializedName("structured_formatting")
        private final StructuredFormatting structuredFormatting;

        @SerializedName("terms")
        private final List<Terms> terms;

        @SerializedName("types")
        private final List<String> types;

        public Predictions(String description, String id, List<MatchedSubstrings> matchedSubstrings,
                           String placeId, String reference, StructuredFormatting structuredFormatting,
                           List<Terms> terms, List<String> types) {
            this.description = description;
            this.id = id;
            this.matchedSubstrings = matchedSubstrings;
            this.placeId = placeId;
            this.reference = reference;
            this.structuredFormatting = structuredFormatting;
            this.terms = terms;
            this.types = types;
        }

        public String getDescription() {
            return description;
        }

        public String getId() {
            return id;
        }

        public List<MatchedSubstrings> getMatchedSubstrings() {
            return matchedSubstrings;
        }

        public String getPlaceId() {
            return placeId;
        }

        public String getReference() {
            return reference;
        }

        public StructuredFormatting getStructuredFormatting() {
            return structuredFormatting;
        }

        public List<Terms> getTerms() {
            return terms;
        }

        public List<String> getTypes() {
            return types;
        }

        public static class MatchedSubstrings {
            @SerializedName("length")
            private final int length;

            @SerializedName("offset")
            private final int offset;

            public MatchedSubstrings(int length, int offset) {
                this.length = length;
                this.offset = offset;
            }

            public int getLength() {
                return length;
            }

            public int getOffset() {
                return offset;
            }
        }

        public static class StructuredFormatting {
            @SerializedName("main_text")
            private final String mainText;

            @SerializedName("main_text_matched_substrings")
            private final List<MainTextMatchedSubstrings> mainTextMatchedSubstrings;

            @SerializedName("secondary_text")
            private final String secondaryText;

            public StructuredFormatting(String mainText,
                                        List<MainTextMatchedSubstrings> mainTextMatchedSubstrings,
                                        String secondaryText) {
                this.mainText = mainText;
                this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
                this.secondaryText = secondaryText;
            }

            public String getMainText() {
                return mainText;
            }

            public List<MainTextMatchedSubstrings> getMainTextMatchedSubstrings() {
                return mainTextMatchedSubstrings;
            }

            public String getSecondaryText() {
                return secondaryText;
            }

            public static class MainTextMatchedSubstrings {
                @SerializedName("length")
                private final int length;

                @SerializedName("offset")
                private final int offset;

                public MainTextMatchedSubstrings(int length, int offset) {
                    this.length = length;
                    this.offset = offset;
                }

                public int getLength() {
                    return length;
                }

                public int getOffset() {
                    return offset;
                }
            }
        }

        public static class Terms {
            @SerializedName("offset")
            private final int offset;

            @SerializedName("value")
            private final String value;

            public Terms(int offset, String value) {
                this.offset = offset;
                this.value = value;
            }

            public int getOffset() {
                return offset;
            }

            public String getValue() {
                return value;
            }
        }
    }
}
