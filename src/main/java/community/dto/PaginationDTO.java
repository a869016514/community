package community.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO <T>{
		private List<T> data;
		private boolean showPrevious;  //前一夜按钮
		private boolean showFirstPage; //第一页
		private boolean showNext;	   //下一页
		private boolean showEndPage;   //最后一页 
		private Integer page;
		private Integer totalPage; 
		private List<Integer> pages=new ArrayList<Integer>();
		
		
		
		public List<T> getData() {
			return data;
		}
		public void setData(List<T> data) {
			this.data = data;
		}
		public boolean isShowPrevious() {
			return showPrevious;
		}
		public void setShowPrevious(boolean showPrevious) {
			this.showPrevious = showPrevious;
		}
		public boolean isShowFirstPage() {
			return showFirstPage;
		}
		public Integer getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}
		public void setShowFirstPage(boolean showFirstPage) {
			this.showFirstPage = showFirstPage;
		}
		public boolean isShowNext() {
			return showNext;
		}
		public void setShowNext(boolean showNext) {
			this.showNext = showNext;
		}
		public boolean isShowEndPage() {
			return showEndPage;
		}
		public void setShowEndPage(boolean showEndPage) {
			this.showEndPage = showEndPage;
		}
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public List<Integer> getPages() {
			return pages;
		}
		public void setPages(List<Integer> pages) {
			this.pages = pages;
		}
		
		
		public void setPagination(Integer totalCount, Integer page, Integer size) { 
			this.page=page; 
			if(totalCount% size==0) {
				totalPage=totalCount/size;
			}else {
				totalPage=totalCount/size +1;
			}
			
			
			//导航条展示
			pages.add(page);  //pages是整条导航条，page是当前页
			
			for(int i = 1;i <= 3;i++) {
				if(page - i > 0) {
					pages.add(0,page - i);  //头部插入
				}
				if(page + i<=totalPage) {
					pages.add(page + i);//尾部插入
				}
			}
			//是否展示上一页
			if(page==1) {
				showPrevious=false;
			}else {
				showPrevious=true;
			}
			//是否展示下一页
			if(page==totalPage) {
				showNext=false;
			}else {
				showNext=true;
			}
			//是否展示第一页
			if(pages.contains(1)) {
				showFirstPage=false;
			}else {
				showFirstPage=true;
			}
			//是否展示最后一页
			if(pages.contains(totalPage)) {
				showFirstPage=false;
			}else {
				showFirstPage=true;
			}
		}
		
}
