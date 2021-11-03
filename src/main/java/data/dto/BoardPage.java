package data.dto;


public class BoardPage {

	public int totalCount;		//�� ����
	public int totalPage;		//�� ������ ��
	public int startPage;		//�� ���� ����������
	public int endPage;		//�� ���� ��������
	public int start; 			//�� �������� ���۹�ȣ
	public int perPage=10;		//�� �������� ������ ���� ����
	public int perBlock=5;		//�� �������� �������� ������ ����
	public int currentPage;	//���� ������
	public int no;				//�Խñ� ��ȣ

	public BoardPage(int totalNum, String currentPage) {
		//�� ����
		totalCount = totalNum;

		//���� ������ ��ȣ �б�
		if(currentPage == null){
			this.currentPage = 1;
		}
		else{

			this.currentPage = Integer.parseInt(currentPage);
		}
		
		//�� ������ ���� ���ϱ�
		totalPage = totalCount/perPage + (totalCount%perPage==0?0:1);

		//�� ���� ���� ������
		//�� : ���� ������:3 startPage:1, endpage:3
		//�� : ���� ������:5 startPage:4, endpage:6

		startPage = (this.currentPage-1)/perBlock*perBlock+1;
		endPage = startPage+perBlock-1;

		if(endPage > totalPage){
			endPage = totalPage;
		}

		//�� ���������� �ҷ��� ���� ��ȣ
		start = (this.currentPage-1)*perPage;

		//�� �� �տ� ���� ���۹�ȣ ���ϱ�
		no = totalCount-(this.currentPage-1)*perPage;
	}

}
