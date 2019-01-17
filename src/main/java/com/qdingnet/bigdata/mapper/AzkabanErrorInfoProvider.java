package com.qdingnet.bigdata.mapper;

import com.qdingnet.bigdata.beans.AzkabanErrorInfo;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author yanpf
 * @date 2019/1/17 15:25
 * @description
 */
public class AzkabanErrorInfoProvider {

    public String insertAzkabanErrorInfoSql(AzkabanErrorInfo errorInfo){
        return new SQL(){
            {
                INSERT_INTO("azkaban_error_info");
                if(errorInfo.getExecId() != null){
                    VALUES("exec_id", "#{execId}");
                }
                if(errorInfo.getProjectId() != null){
                    VALUES("project_id", "#{projectId}");
                }
                if(errorInfo.getName() != null){
                    VALUES("name", "#{name}");
                }
                if(errorInfo.getAttempt() != null){
                    VALUES("attempt", "#{attempt}");
                }
                if(errorInfo.getErrorLog() != null){
                    VALUES("error_log", "#{errorLog}");
                }
                if(errorInfo.getUploadTime() != null){
                    VALUES("upload_time", "#{uploadTime}");
                }
            }
        }.toString();
    }
}
