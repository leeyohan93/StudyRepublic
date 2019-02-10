
package org.mohajo.studyrepublic.fileupload;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MyUploadForm {

	private String description;
	
	private MultipartFile[] fileDatas;
	
}
