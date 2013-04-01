use strict;
use warnings;
use Cwd;

my @ARGV = "";
my $SOURCEFILE;
my $MODFILE;
my $sSourceFileFN;
my $sCharAdd;

if ($#ARGV != 0)
{
	chomp ($sSourceFileFN	= $ARGV[0]);
	chomp ($sCharAdd		= $ARGV[1]);
}
else
{
	my $sDirectory	= 'C:\\Documents and Settings\\matsch\\My Documents\\';
	my $sFile		= 'DTC_CostOfSalesSummary_SL8Prod.csv';
	$sSourceFileFN	= $sDirectory.$sFile;
	
	$sCharAdd		= '0';
}

my $sModFileFN = (split('\.', $sSourceFileFN))[0].'_mod.'.(split('\.', $sSourceFileFN))[1];
print $sModFileFN."\n";
print $sCharAdd."\n";

open($SOURCEFILE, "$sSourceFileFN")	or die("Could not open SOURCE FILE: $!\n");
open($MODFILE,	">$sModFileFN")		or die("Could not open STORE FILE: $!\n");

my @sSourceContents = <$SOURCEFILE>;

my $sModContLine;
foreach (@sSourceContents)
{
	chomp ($sModContLine	= $_);
	$sModContLine			= '0'.$sModContLine if $sModContLine =~ /^\d/;
	printf $MODFILE $sModContLine."\n";
}

close($MODFILE)		or die("Could not close STORE FILE: $!\n");
close($SOURCEFILE)	or die("Could not close SOURCE FILE: $!\n");