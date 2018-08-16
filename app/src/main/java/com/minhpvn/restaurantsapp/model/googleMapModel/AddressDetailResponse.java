package com.minhpvn.restaurantsapp.model.googleMapModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressDetailResponse {
    @SerializedName("results")
    private final List<Results> results;

    @SerializedName("status")
    private final String status;

    public AddressDetailResponse(List<Results> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<Results> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public static class Results {
        @SerializedName("address_components")
        private final List<AddressComponents> addressComponents;

        @SerializedName("formatted_address")
        private final String formattedAddress;

        @SerializedName("geometry")
        private final Results.Geometry geometry;

        @SerializedName("place_id")
        private final String placeId;

        @SerializedName("types")
        private final List<String> types;

        public Results(List<AddressComponents> addressComponents, String formattedAddress,
                       Results.Geometry geometry, String placeId, List<String> types) {
            this.addressComponents = addressComponents;
            this.formattedAddress = formattedAddress;
            this.geometry = geometry;
            this.placeId = placeId;
            this.types = types;
        }

        public List<AddressComponents> getAddressComponents() {
            return addressComponents;
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public Results.Geometry getGeometry() {
            return geometry;
        }

        public String getPlaceId() {
            return placeId;
        }

        public List<String> getTypes() {
            return types;
        }

        public static class AddressComponents {
            @SerializedName("long_name")
            private final String longName;

            @SerializedName("short_name")
            private final String shortName;

            @SerializedName("types")
            private final List<String> types;

            public AddressComponents(String longName, String shortName, List<String> types) {
                this.longName = longName;
                this.shortName = shortName;
                this.types = types;
            }

            public String getLongName() {
                return longName;
            }

            public String getShortName() {
                return shortName;
            }

            public List<String> getTypes() {
                return types;
            }
        }

        public static class Geometry {
            @SerializedName("bounds")
            private final Results.Geometry.Bounds bounds;

            @SerializedName("location")
            private final Results.Geometry.Location location;

            @SerializedName("location_type")
            private final String locationType;

            @SerializedName("viewport")
            private final Results.Geometry.Viewport viewport;

            public Geometry(Results.Geometry.Bounds bounds, Results.Geometry.Location location, String locationType,
                            Results.Geometry.Viewport viewport) {
                this.bounds = bounds;
                this.location = location;
                this.locationType = locationType;
                this.viewport = viewport;
            }

            public Results.Geometry.Bounds getBounds() {
                return bounds;
            }

            public Results.Geometry.Location getLocation() {
                return location;
            }

            public String getLocationType() {
                return locationType;
            }

            public Results.Geometry.Viewport getViewport() {
                return viewport;
            }

            public static class Bounds {
                @SerializedName("northeast")
                private final Results.Geometry.Bounds.Northeast northeast;

                @SerializedName("southwest")
                private final Results.Geometry.Bounds.Southwest southwest;

                public Bounds(Results.Geometry.Bounds.Northeast northeast, Results.Geometry.Bounds.Southwest southwest) {
                    this.northeast = northeast;
                    this.southwest = southwest;
                }

                public Results.Geometry.Bounds.Northeast getNortheast() {
                    return northeast;
                }

                public Results.Geometry.Bounds.Southwest getSouthwest() {
                    return southwest;
                }

                public static class Northeast {
                    @SerializedName("lat")
                    private final double lat;

                    @SerializedName("lng")
                    private final double lng;

                    public Northeast(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }

                public static class Southwest {
                    @SerializedName("lat")
                    private final double lat;

                    @SerializedName("lng")
                    private final double lng;

                    public Southwest(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }
            }

            public static class Location {
                @SerializedName("lat")
                private final double lat;

                @SerializedName("lng")
                private final double lng;

                public Location(double lat, double lng) {
                    this.lat = lat;
                    this.lng = lng;
                }

                public double getLat() {
                    return lat;
                }

                public double getLng() {
                    return lng;
                }
            }

            public static class Viewport {
                @SerializedName("northeast")
                private final Results.Geometry.Viewport.Northeast northeast;

                @SerializedName("southwest")
                private final Results.Geometry.Viewport.Southwest southwest;

                public Viewport(Results.Geometry.Viewport.Northeast northeast, Results.Geometry.Viewport.Southwest southwest) {
                    this.northeast = northeast;
                    this.southwest = southwest;
                }

                public Results.Geometry.Viewport.Northeast getNortheast() {
                    return northeast;
                }

                public Results.Geometry.Viewport.Southwest getSouthwest() {
                    return southwest;
                }

                public static class Northeast {
                    @SerializedName("lat")
                    private final double lat;

                    @SerializedName("lng")
                    private final double lng;

                    public Northeast(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }

                public static class Southwest {
                    @SerializedName("lat")
                    private final double lat;

                    @SerializedName("lng")
                    private final double lng;

                    public Southwest(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public double getLng() {
                        return lng;
                    }
                }
            }
        }
    }
}
