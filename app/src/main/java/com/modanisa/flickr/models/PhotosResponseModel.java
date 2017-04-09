package com.modanisa.flickr.models;

/**
 * Created by muhammadkorany on 4/7/17.
 */

public class PhotosResponseModel {

    private String stat;
    private photosResponse photos;
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public photosResponse getPhotos() {
        return photos;
    }

    public void setPhotos(photosResponse photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public class photosResponse {
        private int page;
        private int pages;
        private int perpage;
        private String total;

        private PhotoModel[] photo;

        public PhotoModel[] getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoModel[] photo) {
            this.photo = photo;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPerpage() {
            return perpage;
        }

        public void setPerpage(int perpage) {
            this.perpage = perpage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
