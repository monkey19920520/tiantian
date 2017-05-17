public class BowlingGame {

    public int getBowlingScore(String bowlingCode) {
		String[] split=bowlingCode.split("[|]"); //with '|' split
		int score=0;                             //initialize score=0;
		/*
		 * judge whether it has additional opportunities
		 */
		if(split[split.length-1].contains("-")){ //there is no additional opportunities
			for(int i=0;i<split.length;i++){     
				score=calMissScore(split, i, score);
			}
		}else{                                   //there is additional opportunities
			for(int i=0;i<split.length-2;i++){
				/*
				 * split[i]can be strike,spare or miss
				 */
				if(split[i].equals("X")){
					score+=10;
					score=calStrikeScore(split, i, score);
				}else if(split[i].contains("/")){
					score+=10;
					score=calSpareScore(split, i, score);
				}else{
					score=calMissScore(split, i, score);
				}
			}
					
		}
        return score;
    }
	//calculate the score of strike in split[i]
	public int calStrikeScore(String[] split, int i, int strikeScore){
		if(split[i+1].equals("X")){                //when split[i+1] is "X" need to consider split[i+2] 
			strikeScore+=10;
			if(split[i+2].equals("X")){
				strikeScore+=10;
			}else if(split[i+2].equals("")){
				if(split[i+3].equals("XX")){
					strikeScore+=10;
				}else{
					strikeScore=subCal(split, i+2, strikeScore);
				}	
			}else{
				strikeScore=subCal(split, i+1, strikeScore);
			}
		}else{                                   //when split[i+1] is "" or is not "X"
			if(split[i+1].equals("")){           //split[i+1] is ""
				if(split[i+2].equals("XX")){
					strikeScore+=20;
				}else{
					 String subStr1=split[i+2].substring(0, 1);
					 String subStr2=split[i+2].substring(1, 2);
					 strikeScore+=(Integer.parseInt(subStr1)+Integer.parseInt(subStr2));
				}
				
			}else{                              //split[i+1] is not "" and is not "X"
				  if(split[i+1].contains("/")){
					  strikeScore+=10;
				  }else{
					  String subStr1=split[i+1].substring(0, 1);
					  String subStr2=split[i+1].substring(1, 2);
					  if(subStr1.equals("-")){
						  strikeScore+=Integer.parseInt(subStr2);
					  }else{
						  strikeScore+=Integer.parseInt(subStr1);
					  }
				  }
			}
		}
		return strikeScore;
	}
	//calculate the score of spare in split[i]
	public int calSpareScore(String[] split, int i, int spareScore){
		/*
		 * judge split[i+1]
		 */
		if(split[i+1].equals("X")){
			spareScore+=10;
		}else if(split[i+1].equals("")){
			spareScore+=Integer.parseInt(split[i+2]);
		}else{
			spareScore=subCal(split, i, spareScore);
		}
		return spareScore;
	}
	//calculate the score of miss in split[i]
	public int calMissScore(String[] split, int i, int missScore){
		//according to split[i] to calculate the score of miss 
		String subStr1=split[i].substring(0, 1);
		String subStr2=split[i].substring(1, 2);
		if(subStr1.equals("-")){
			missScore+=Integer.parseInt(subStr2);
		}else{
			missScore+=Integer.parseInt(subStr1);
		}
		return missScore;
	}
	//calculate the score of in strike or miss when need to consider the next of split[i]
	public int subCal(String[] split, int i, int subScore){
		String subStr1=split[i+1].substring(0, 1);
		String subStr2=split[i+1].substring(1, 2);
		if(subStr1.equals("-")){
			subScore+=0;
		}else if(subStr2.equals("-")){
			subScore+=Integer.parseInt(subStr1);
		}else if(subStr1.equals("/")){
			subScore+=(10-Integer.parseInt(subStr2));
		}else{
			subScore+=Integer.parseInt(subStr1);
		}
		return subScore;
	}
}
