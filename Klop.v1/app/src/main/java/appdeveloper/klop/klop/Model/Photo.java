package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id_photo")
    @Expose
    private String idPhoto;
    @SerializedName("id_store")
    @Expose
    private String idStore;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("created")
    @Expose
    private String created;

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
