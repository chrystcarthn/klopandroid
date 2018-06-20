package appdeveloper.klop.klop.Presenter;

import appdeveloper.klop.klop.Model.Like;

/**
 * Created by CMDDJ on 4/22/2018.
 */

public interface ReviewTabFragPresenter {
    void showReview(String strIdStore);

    void like(String strIdReview, String strIdUser);
    void unlike(String strIdReview, String strIdUser);
    void checkLike(String strIdReview, String strIdUser);
    void checkLikeForToggle(String strIdReview, String strIdUser);

    void changeLike(String strIdReview, String strIdUser);


}
