CREATE TABLE `stats` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `wowzaId` int(20) NOT NULL,
  `shibId` text NOT NULL,
  `server` text NOT NULL,
  `ip` varchar(15) NOT NULL,
  `connectTime` int(20) NOT NULL,
  `disconnectTime` int(20) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `wowzaId` (`wowzaId`)
);

CREATE TABLE `log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `wowzaId` int(20) NOT NULL,
  `shibId` text NOT NULL,
  `server` text NOT NULL,
  `ip` varchar(15) NOT NULL,
  `error` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `wowzaId` (`wowzaId`)
);
