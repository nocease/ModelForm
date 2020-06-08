IF EXISTS(SELECT *
          FROM sys.all_objects
          WHERE object_id = OBJECT_ID(N''[dbo].[mf_baidu_page]'')
            AND type IN (''U''))
    DROP TABLE [dbo].[mf_baidu_page];
CREATE TABLE [dbo].[mf_baidu_page]
(
    [pageid] VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_id]  VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_baidu_page]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_baidu_statistics]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_baidu_statistics];
CREATE TABLE [dbo].[mf_baidu_statistics]
(
    [bd_id]             VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_time]           VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_website]        VARCHAR(255) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_domain]         VARCHAR(255) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_msg]            VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [bd_statistics_url] VARCHAR(255) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [bd_js]             VARCHAR(500) COLLATE Chinese_PRC_CI_AS NOT NULL
);
ALTER TABLE [dbo].[mf_baidu_statistics]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_field]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_field];
CREATE TABLE [dbo].[mf_field]
(
    [field_id]           VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [form_id]            VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL,
    [field_time]         VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL,
    [field_type]         VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NULL,
    [field_zz_test]      VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [field_zz_desc]      VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [field_filetype]     VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NULL,
    [field_timetype]     VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NULL,
    [field_name]         VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [field_name_desc]    VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [field_select_value] VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_field]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_form]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_form];
CREATE TABLE [dbo].[mf_form]
(
    [form_id]        VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [form_name]      VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [form_tablename] VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [form_describe]  VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [form_time]      VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_form]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_page]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_page];
CREATE TABLE [dbo].[mf_page]
(
    [id]           VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [ctime]        VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [title]        VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [message]      VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [formid]       VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [importscript] text COLLATE Chinese_PRC_CI_AS         NULL,
    [pagetype]     VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [canuse]       VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [html_width]   VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [pagelimit]    VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_page]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_page_set_add]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_page_set_add];
CREATE TABLE [dbo].[mf_page_set_add]
(
    [pageid]       VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [fieldid]      VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [ispost]       VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [fieldstate]   VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [defaultvalue] VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL,
    [orderby]      VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_page_set_add]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_page_set_list]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_page_set_list];
CREATE TABLE [dbo].[mf_page_set_list]
(
    [pageid]     VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [fieldid]    VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [can_sort]   VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [can_search] VARCHAR(1) COLLATE Chinese_PRC_CI_AS   NOT NULL,
    [orderby]    VARCHAR(100) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_page_set_list]
    SET ( LOCK_ESCALATION = TABLE );
IF
    EXISTS(SELECT *
           FROM sys.all_objects
           WHERE object_id = OBJECT_ID(N''[dbo].[mf_value]'')
             AND type IN (''U''))
    DROP TABLE [dbo].[mf_value];
CREATE TABLE [dbo].[mf_value]
(
    [fieldid] VARCHAR(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
    [value]   VARCHAR(255) COLLATE Chinese_PRC_CI_AS NULL
);
ALTER TABLE [dbo].[mf_value]
    SET ( LOCK_ESCALATION = TABLE );
ALTER TABLE [dbo].[mf_baidu_page]
    ADD CONSTRAINT [PK__mf_baidu__54B0FB5C7F60ED59] PRIMARY KEY CLUSTERED ([pageid]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_baidu_statistics]
    ADD CONSTRAINT [PK__mf_baidu__EC38960A03317E3D] PRIMARY KEY CLUSTERED ([bd_id]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_field]
    ADD CONSTRAINT [PK__mf_field__1BB6F43E07020F21] PRIMARY KEY CLUSTERED ([field_id]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_form]
    ADD CONSTRAINT [PK__mf_form__190E16C90AD2A005] PRIMARY KEY CLUSTERED ([form_id]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_page]
    ADD CONSTRAINT [PK__mf_page__3213E83F0EA330E9] PRIMARY KEY CLUSTERED ([id]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_page_set_add]
    ADD CONSTRAINT [PK__mf_page___F0AD1BE61273C1CD] PRIMARY KEY CLUSTERED ([fieldid]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];
ALTER TABLE [dbo].[mf_page_set_list]
    ADD CONSTRAINT [PK__mf_page___F0AD1BE6164452B1] PRIMARY KEY CLUSTERED ([fieldid]) WITH ( PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON ) ON [PRIMARY];