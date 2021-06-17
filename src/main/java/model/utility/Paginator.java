package model.utility;

public class Paginator {
    private int limit;
    private int offset;

    public Paginator(int page, int itemPage){
        limit=itemPage;
        offset=(page-1) * (itemPage + 1);
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getPages(int size){
        int additionalPage=(size % limit ==0) ? 0 : 1;
        return (size/limit) + additionalPage;
    }
}
