package android.buzit.twitterlike.model;

/**
 * Created by buzit on 25/10/2017.
 */

public class Session {
        private String token;

        /**
         * Constructeur privé
         */
        private Session(){}

        /**
         * Session unique non préinitialisée
         */
        private static Session session = null;

        public static Session getInstance(){
            if (session == null) {
                session = new Session();
            }
            return session;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
    }
}
