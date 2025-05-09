USE [LibraryManagement]
GO
/****** Object:  Table [dbo].[Authors]    Script Date: 4/29/2025 10:11:48 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Authors](
	[AuthorID] [int] IDENTITY(1,1) NOT NULL,
	[AuthorName] [nvarchar](500) NULL,
	[IsDelete] [int] NOT NULL,
	[CreatedDt] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[UpdateDt] [datetime] NOT NULL,
	[UpdateBy] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Authors] PRIMARY KEY CLUSTERED 
(
	[AuthorID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Authors] ON 

INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (1, N'Angela Duckworth', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (2, N'Brené Brown', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (3, N'Carol Dweck', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (4, N'Charles Duhigg', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (5, N'Dalai Lama', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (6, N'Dale Carnegie', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (7, N'David Schwartz', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (8, N'Don Miguel Ruiz', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (9, N'Eckhart Tolle', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (10, N'Eric Ries', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (11, N'James Clear', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (12, N'Jen Sincero', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (13, N'John Doe', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (14, N'M. Scott Peck', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (15, N'Malcolm Gladwell', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (16, N'Mark Manson', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (17, N'Michelle Obama', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (18, N'Paulo Coelho', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (19, N'Rhonda Byrne', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (20, N'Robert Greene', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (21, N'Simon Sinek', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (22, N'Stephen Covey', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (23, N'Susan Cain', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (24, N'Tara Westover', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
INSERT [dbo].[Authors] ([AuthorID], [AuthorName], [IsDelete], [CreatedDt], [CreatedBy], [UpdateDt], [UpdateBy]) VALUES (25, N'Yuval Noah Harari', 0, CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com', CAST(N'2025-04-27T09:45:21.030' AS DateTime), N'admin@uit.com')
SET IDENTITY_INSERT [dbo].[Authors] OFF
GO
