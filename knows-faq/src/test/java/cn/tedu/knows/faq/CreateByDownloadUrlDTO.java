package cn.tedu.knows.faq;

public class CreateByDownloadUrlDTO {

        /**
         * passport user id -> SinoClick 用户id
         */
        private Long passportUserId;
        /**
         * 素材名称
         */
        private String name;
        /**
         * 素材类型
         * @see cn.sino.cb.cmp.assemble.service.enumeration.workcollect.WorkCollectType
         */
        private Integer type;

        /**
         * 文件下载url
         */
        private String downloadUrl;

        /**
         * 封面图下载地址
         */
        private String downloadCoverImageUrl;

        /**
         * 自定义值
         */
        private String customValue;

        /**
         * 媒体类型
         */
        private String mediaType;
    }
