USE [VegeShop]
GO

/****** Object:  Table [dbo].[tblUsers]    Script Date: 07/03/2022 10:36:40 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblUsers](
	[userID] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[fullName] [nvarchar](50) NOT NULL,
	[role] [int] NOT NULL,
	[phone] [varchar](50) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblUsers] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
-- ============================================================================================

USE [VegeShop]
GO

/****** Object:  Table [dbo].[tblCategories]    Script Date: 07/03/2022 10:38:14 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblCategories](
	[categoryID] [int] NOT NULL,
	[categoryName] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tblCategories] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

-- =============================================================================================


USE [VegeShop]
GO

/****** Object:  Table [dbo].[tblProducts]    Script Date: 07/03/2022 10:37:32 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblProducts](
	[productID] [int] NOT NULL,
	[productName] [nvarchar](50) NOT NULL,
	[image] [nvarchar](250) NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [int] NOT NULL,
	[categoryID] [int] NOT NULL,
	[isActive] [int] NOT NULL,
 CONSTRAINT [PK_tblProducts] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[tblProducts] ADD  CONSTRAINT [DF_tblProducts_isActive]  DEFAULT ((1)) FOR [isActive]
GO

ALTER TABLE [dbo].[tblProducts]  WITH CHECK ADD  CONSTRAINT [FK_tblProducts_tblCategories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[tblCategories] ([categoryID])
GO

ALTER TABLE [dbo].[tblProducts] CHECK CONSTRAINT [FK_tblProducts_tblCategories]
GO

-- ============================================================================================

USE [VegeShop]
GO

/****** Object:  Table [dbo].[tblOrders]    Script Date: 07/03/2022 10:39:31 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblOrders](
	[orderID] [varchar](50) NOT NULL,
	[userID] [varchar](50) NOT NULL,
	[date] [datetime] NOT NULL,
	[email] [varchar](50) NOT NULL,
	[phone] [varchar](50) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
	[total] [int] NOT NULL,
 CONSTRAINT [PK_tblOrder] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[tblOrders]  WITH CHECK ADD  CONSTRAINT [FK_tblOrders_tblUsers] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUsers] ([userID])
GO

ALTER TABLE [dbo].[tblOrders] CHECK CONSTRAINT [FK_tblOrders_tblUsers]
GO

-- ===========================================================================================

USE [VegeShop]
GO

/****** Object:  Table [dbo].[tblOrderDetails]    Script Date: 07/03/2022 10:40:01 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[tblOrderDetails](
	[orderID] [varchar](50) NOT NULL,
	[productID] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [int] NOT NULL,
 CONSTRAINT [PK_tblOrderDetails] PRIMARY KEY CLUSTERED 
(
	[orderID] ASC,
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[tblOrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_tblOrderDetails_tblOrders] FOREIGN KEY([orderID])
REFERENCES [dbo].[tblOrders] ([orderID])
GO

ALTER TABLE [dbo].[tblOrderDetails] CHECK CONSTRAINT [FK_tblOrderDetails_tblOrders]
GO

ALTER TABLE [dbo].[tblOrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_tblOrderDetails_tblProducts] FOREIGN KEY([productID])
REFERENCES [dbo].[tblProducts] ([productID])
GO

ALTER TABLE [dbo].[tblOrderDetails] CHECK CONSTRAINT [FK_tblOrderDetails_tblProducts]
GO

-- ===========================================================================================

USE [VegeShop]
GO

INSERT INTO [dbo].[tblUsers]
           ([userID]
           ,[password]
           ,[email]
           ,[fullName]
           ,[role]
           ,[phone]
           ,[address])
     VALUES
           ('admin'
           ,'0'
           ,'nguyennhathuy.orit@gmail.com'
           ,'Lil Huy'
           ,1
           ,'0355279240'
           ,'LA')
GO

USE [VegeShop]
GO

INSERT INTO [dbo].[tblCategories]
           ([categoryID]
           ,[categoryName])
     VALUES
           (1
           ,N'Trái cây')
GO

USE [VegeShop]
GO

INSERT INTO [dbo].[tblProducts]
           ([productID]
           ,[productName]
           ,[image]
           ,[quantity]
           ,[price]
           ,[categoryID]
           ,[isActive])
     VALUES
           (1
           ,N'Cam vàng'
           ,'https://cdn.tgdd.vn/Products/Images/8788/202933/bhx/cam-vang-valencia-uc-hop-1kg-4-5-trai-202101271645521884.jpg'
           ,10
           ,49000
           ,1
           ,1)
GO

USE [VegeShop]
GO

INSERT INTO [dbo].[tblProducts]
           ([productID]
           ,[productName]
           ,[image]
           ,[quantity]
           ,[price]
           ,[categoryID]
           ,[isActive])
     VALUES
           (2
           ,N'Táo Gala'
           ,'https://cdn.tgdd.vn/Products/Images/8788/202932/bhx/tao-gala-nhap-khau-new-zealand-tui-1kg-6-7-trai-202101271720534940.jpg'
           ,10
           ,85000
           ,1
           ,1)
GO

USE [VegeShop]
GO

INSERT INTO [dbo].[tblCategories]
           ([categoryID]
           ,[categoryName])
     VALUES
           (2
           ,N'Rau củ')
GO

INSERT INTO [dbo].[tblProducts]
           ([productID]
           ,[productName]
           ,[image]
           ,[quantity]
           ,[price]
           ,[categoryID]
           ,[isActive])
     VALUES
           (3
           ,N'Cà rốt Đà Lạt'
           ,'https://cdn.tgdd.vn/Products/Images/8785/271572/bhx/ca-rot-da-lat-tui-500g-2-4-cu-202202171317027842.jpg'
           ,10
           ,12500
           ,2
           ,1)
GO

USE [VegeShop]
GO