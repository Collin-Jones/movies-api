package data;


public class MoviesDaoFactory {

    // private static Config config = new Config();

    public enum DAOType {MYSQL, IN_MEMORY}

    ; //Notice we have two values here

    public static InMemoryMoviesDao getMoviesDao(DAOType daoType) {

        switch (daoType) {
            case IN_MEMORY: { //yet we have one switch case. We'll get to that!
                return new InMemoryMoviesDao();
            }
        }
        return null;
    }
}

