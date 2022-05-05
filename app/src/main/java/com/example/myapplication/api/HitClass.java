package com.example.myapplication.api;




import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HitClass {


    @Override
    public String toString() {
        return "HitsClass{" +
                "from=" + from +
                ", to=" + to +
                ", more=" + more +
                ", count=" + count +
                ", hits=" + hits +
                '}';
    }

    @SerializedName("from")
    @Expose
    private int from;
    @SerializedName("to")
    @Expose
    private int to;
    @SerializedName("more")
    @Expose
    private boolean more;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("hits")
    @Expose
    private List<Hit> hits = null;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean getMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }



/*
public static class Hit{
    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    @SerializedName("bookmarked")
    @Expose
    private boolean bookmarked;

    @SerializedName("bought")
    @Expose
    private boolean bought;

    public Hit() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean getBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "recipe=" + recipe +
                ", bookmarked=" + bookmarked +
                ", bought=" + bought +
                '}';
    }




    public static class Recipe {

        @SerializedName("uri")
        @Expose
        private String uri;
        @SerializedName("label")
        @Expose
        private String label;

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("source")
        @Expose
        private String source;

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("shareAs")
        @Expose
        private String shareAs;
        @SerializedName("yield")
        @Expose
        private double yield;

        @SerializedName("dietLabels")
        @Expose
        private String[] dietLabels;

        @SerializedName("healthLabels")
        @Expose
        private String[] healthLabels;

        @SerializedName("cautions")
        @Expose
        private String[] cautions;

        public String[] getCautions() {
            return cautions;
        }

        public void setCautions(String[] cautions) {
            this.cautions = cautions;
        }

        public String[] getIngredientLines() {
            return ingredientLines;


        }

        public void setIngredientLines(String[] ingredientLines) {
            this.ingredientLines = ingredientLines;
        }

        @SerializedName("ingredientLines")
        @Expose
        private String[] ingredientLines;

        @SerializedName("ingredients")
        @Expose
        private List<Ingredient> ingredients = null;

        @SerializedName("calories")
        @Expose
        private double calories;

        @SerializedName("totalWeight")
        @Expose
        private double totalWeight;
        @SerializedName("totalTime")
        @Expose
        private double totalTime;

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public double getCalories() {
            return calories;
        }

        public void setCalories(double calories) {
            this.calories = calories;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void setTotalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
        }

        public double getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(double totalTime) {
            this.totalTime = totalTime;
        }

        @Override
        public String toString() {
            return "Recipe{" +
                    "uri='" + uri + '\'' +
                    ", label='" + label + '\'' +
                    ", image='" + image + '\'' +
                    ", source='" + source + '\'' +
                    ", url='" + url + '\'' +
                    ", shareAs='" + shareAs + '\'' +
                    ", yield=" + yield +
                    ", dietLabels=" + Arrays.toString(dietLabels) +
                    ", healthLabels=" + Arrays.toString(healthLabels) +
                    ", cautions=" + Arrays.toString(cautions) +
                    ", ingredientLines=" + Arrays.toString(ingredientLines) +
                    ", ingredients=" + ingredients +
                    ", calories=" + calories +
                    ", totalWeight=" + totalWeight +
                    ", totalTime=" + totalTime +
                    '}';
        }

        public class Ingredient{

            @SerializedName("text")
            @Expose
            private String text;

            @SerializedName("weight")
            @Expose
            private double weight;

            @SerializedName("image")
            @Expose
            private String image;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public String[] getDietLabels() {
            return dietLabels;
        }

        public void setDietLabels(String[] dietLabels) {
            this.dietLabels = dietLabels;
        }

        public String[] getHealthLabels() {
            return healthLabels;
        }

        public void setHealthLabels(String[] healthLabels) {
            this.healthLabels = healthLabels;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getShareAs() {
            return shareAs;
        }

        public void setShareAs(String shareAs) {
            this.shareAs = shareAs;
        }

        public double getYield() {
            return yield;
        }

        public void setYield(double yield) {
            this.yield = yield;
        }
    }

}*/
    


}


