<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="3.0" xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="#all">
    <xsl:output method="text" indent="true"/>
    <xsl:param name="publisherId" as="xs:string"/>
    <xsl:param name="jcode" as="xs:string"/>
    <xsl:param name="sectionPath" as="xs:string"/>
    <!-- Start the transformation with the root element -->
    <xsl:variable name="input_document" select="doc(concat('http://staticfs.highwire.org/',$jcode,'/ads/site-ads.xml'))"/>
<xsl:template match="$input_document/*:SectionSet">
{
   "publisherId":"<xsl:value-of select="$publisherId"/>",
    "journlcode":"<xsl:value-of select="$jcode"/>",
<xsl:for-each select="$input_document//*:Section[*:RegexSet/*:Regex[contains(@prefix, 'yes') and matches(., '^/$')]]">
    <xsl:if test="position()=1">
   "section":[
    </xsl:if>{
                "sectionId": "<xsl:value-of select='*:SectionId'/>",
                <xsl:apply-templates select="*:RegexSet" />
                <xsl:apply-templates select="*:LocationSet" />
              },
</xsl:for-each>
<xsl:variable name="sectionPathIdentify">
    <xsl:choose>
        <xsl:when test="matches($sectionPath, '^/content/(\d+)/(\d+)(/?)$')"><xsl:value-of select="'/content/vol.*/issue.*/'"/></xsl:when>
        <xsl:when test="matches($sectionPath, '^/content/(\d+)/(\d+)\.aindex(/?)$') or matches($sectionPath, '^/content/(\d+)/(\d+)\.author-index(/?)$')"><xsl:value-of select="'/content/vol.*/issue.*/aindex.shtml'"/></xsl:when>
        <xsl:when test="matches($sectionPath, '^/content/(\d+)/(\d+)\.index(/?)$')"><xsl:value-of select="'/content/vol.*/issue.*/index.shtml'"/></xsl:when>
        <xsl:when test="matches($sectionPath, '^/content/current(/?)$')"><xsl:value-of select="'/current.shtml'"/></xsl:when>
        <xsl:otherwise><xsl:value-of select="$sectionPath"/></xsl:otherwise>
    </xsl:choose>
</xsl:variable>
    <xsl:message select="$sectionPathIdentify"></xsl:message>

<xsl:for-each select="//*:Section[*:RegexSet/*:Regex[contains(@prefix, 'yes') and matches(., concat('^', $sectionPathIdentify, '$'))]]">
            {
                "sectionId": "<xsl:value-of select='*:SectionId'/>",
                <xsl:apply-templates select="*:RegexSet" />
                <xsl:apply-templates select="*:LocationSet" />
            }
</xsl:for-each>
            ]
}
</xsl:template>
    
<xsl:template match="*:RegexSet">
                "sectionPath": [<xsl:for-each select="*:Regex">"<xsl:value-of select='text()'/>"<xsl:if test="position() != last()">,</xsl:if></xsl:for-each>],
</xsl:template>
    
    <xsl:template match="*:LocationSet">
        <xsl:apply-templates select="*:Location" />
    </xsl:template>
    <xsl:template match="*:Location">
        <xsl:if test="position() = 1">
                "adLocation":[
        </xsl:if>
                            {
                            <xsl:apply-templates select="*:Position"/>
                            <xsl:apply-templates select="*:AdSet"/>
                            }<xsl:if test="position() != last()">,</xsl:if><xsl:if test="position() = last()">]</xsl:if>
    </xsl:template>
    
    <xsl:template match="*:AdSet">
        <xsl:apply-templates select="*:Ad" />
    </xsl:template>
    
            <xsl:template match="*:Position">
                            "positionId": "<xsl:value-of select='*:PosId'/>",
                            "positionName": "<xsl:value-of select='*:PosName'/>"<xsl:if test="following-sibling::*:AdSet">,</xsl:if>
            </xsl:template>
    
                        <xsl:template match="*:Ad">
                            <xsl:if test="position()=1">
                             "adData":[
                            </xsl:if>
                                        {
                                           "adString": "<xsl:call-template name='rawAdString'/>",
                                           "adHtml": "<xsl:call-template name='renderAdHtml'/>",
                                           "adId": "<xsl:value-of select='*:AdId'/>",
                                           "adStartDate": "<xsl:value-of select='*:StartDateTime'/>",
                                           "adEndDate": "<xsl:value-of select='*:EndDateTime'/>",
                                           "adFrequency": "<xsl:value-of select='*:Frequency'/>"
                                         }<xsl:if test="position() != last()">,</xsl:if>
        <xsl:if test="position()=last()">]  
                            </xsl:if>
                        </xsl:template>
    
    
    <xsl:template name="rawAdString">
        <xsl:value-of select="replace(*:AdString,'&quot;','\\&quot;')" disable-output-escaping="no"/>
    </xsl:template>
    
    <xsl:template name="renderAdHtml">
        <xsl:apply-templates select="*:AdHtml/node()"/>
    </xsl:template>
    
    <xsl:template match="@*">
        <xsl:text disable-output-escaping="yes"> </xsl:text>
        <xsl:value-of select="name()"/>
        <xsl:text disable-output-escaping="yes">="</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text disable-output-escaping="yes">"</xsl:text>
    </xsl:template>
    
    <xsl:template match="*:AdHtml/descendant-or-self::*">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text>
        <xsl:value-of select="name()"/>
        <xsl:apply-templates select="@*"/>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
        <xsl:apply-templates select="node()"/>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text>
        <xsl:value-of select="name()"/>
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    
    <xsl:template match="text()">
        <xsl:value-of select="normalize-space(.)"/>
    </xsl:template>
    <xsl:template match="/">
        <xsl:apply-templates select="$input_document/*:SectionSet"/>
    </xsl:template>
</xsl:stylesheet>
