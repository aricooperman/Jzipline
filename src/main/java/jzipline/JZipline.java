package jzipline;


import java.io.File;

//import errno
//import os
//from functools import wraps
//
//import click
//import logbook
//import pandas as pd

import jzipline.data.bundles.Bundles;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

//import jzipline.utils.Paths;
//import jzipline.utils.RunAlgo;// import _run, load_extensions

public class JZipline {

	//def extract_option_object(option):
	//    """Convert a click.option call into a click.Option object.
	//
	//    Parameters
	//    ----------
	//    option : decorator
	//        A click.option decorator.
	//
	//    Returns
	//    -------
	//    option_object : click.Option
	//        The option object that this decorator will create.
	//    """
	//    @option
	//    def opt():
	//        pass
	//
	//    return opt.__click_params__[0]
	
	
	public static class Run {
	
	
		public static void main( String[] args ) {

			final CommandLineParser parser = new DefaultParser();

			// create the Options
			final Options options = new Options();
			options.addOption( Option.builder( "f" )
					.longOpt( "algofile" )
                    .desc( "The file that contains the algorithm to run." )
                    .hasArg()
                    .required()
                    .type( File.class )
                    .argName( "ALGO_FILE" )
                    .build() );
			
//			options.addOption( Option.builder( "t" )
//					.longOpt( "algotext" )
//                    .desc( "The algorithm script to run." )
//                    .hasArg()
//                    .argName( "SCRIPT" )
//                    .build() );
			
			options.addOption( Option.builder( "D" )
					.longOpt( "define" )
                    .desc( "Define a name to be bound in the namespace before executing" +
				    " the algotext. For example '-Dname=value'. The value may be any python" +
				    " expression. These are evaluated in order so they may refer to previously" +
				    " defined names." )
                    .hasArgs()
                    .argName( "NAME=VALUE" )
                    .build() );
			
//			@click.option(
			//	    '--data-frequency',
			//	    type=click.Choice({'daily', 'minute'}),
			//	    default='daily',
			//	    show_default=True,
			//	    help='The data frequency of the simulation.',
			//	)
			//	@click.option(
			//	    '--capital-base',
			//	    type=float,
			//	    default=10e6,
			//	    show_default=True,
			//	    help='The starting capital for the simulation.',
			//	)
			//	@click.option(
			//	    '-b',
			//	    '--bundle',
			//	    default='quantopian-quandl',
			//	    metavar='BUNDLE-NAME',
			//	    show_default=True,
			//	    help='The data bundle to use for the simulation.',
			//	)
			//	@click.option(
			//	    '--bundle-timestamp',
			//	    type=Timestamp(),
			//	    default=pd.Timestamp.utcnow(),
			//	    show_default=False,
			//	    help='The date to lookup data on or before.\n'
			//	    '[default: <current-time>]'
			//	)
			options.addOption( Option.builder( "s" )
					.longOpt( "start" )
                    .desc( "The start date of the simulation." )
                    .hasArg()
                    .required()
//                    .type( LocalDate.class )
                    .argName( "START" )
                    .build() );

			options.addOption( Option.builder( "e" )
					.longOpt( "end" )
                    .desc( "The end date of the simulation." )
                    .hasArg()
//                    .required()
//                    .type( LocalDate.class )
                    .argName( "END" )
                    .build() );
			
			//	@click.option(
			//	    '-o',
			//	    '--output',
			//	    default='-',
			//	    metavar='FILENAME',
			//	    show_default=True,
			//	    help="The location to write the perf data. If this is '-' the perf will"
			//	    " be written to stdout.",
			//	)
			//	@click.option(
			//	    '--print-algo/--no-print-algo',
			//	    is_flag=True,
			//	    default=False,
			//	    help='Print the algorithm to stdout.',
			//	)
			
			try {
				final CommandLine line = parser.parse( options, args );

				final String startS = line.getOptionValue( 's' );
				final LocalDate start = LocalDate.parse( startS );
				System.out.println( "Start = " + start );
				
				final String endS = line.getOptionValue( 'e' );
                final LocalDate end = StringUtils.isNotBlank( endS ) ? LocalDate.parse( endS ) : LocalDate.now();
                System.out.println( "End = " + end );
                
                if( start.isAfter( end ) )
                    throw new IllegalArgumentException( "Start date is after end date" );
				
                //			ctx,
                File algofile = (File)line.getParsedOptionValue( "algofile" );
                //	        define,
                //	        data_frequency,
                //	        capital_base,
                //	        bundle,
                //	        bundle_timestamp,
                //	        output,
                //	        print_algo,
                //	        local_namespace
				
                //	    Run a backtest for the given algorithm.
				//	
				//	    perf = _run(
				//	        initialize=None,
				//	        handle_data=None,
				//	        before_trading_start=None,
				//	        analyze=None,
				//	        algofile=algofile,
				//	        algotext=algotext,
				//	        defines=define,
				//	        data_frequency=data_frequency,
				//	        capital_base=capital_base,
				//	        data=None,
				//	        bundle=bundle,
				//	        bundle_timestamp=bundle_timestamp,
				//	        start=start,
				//	        end=end,
				//	        output=output,
				//	        print_algo=print_algo,
				//	        local_namespace=local_namespace,
				//	        environ=os.environ,
				//	    )
				//	
				//	    if output == '-':
				//	        click.echo(str(perf))
				//	    elif output != os.devnull:  # make the zipline magic not write any data
				//	        perf.to_pickle(output)
				//	
				//	    return perf
			}
			catch( ParseException e ) {
			    System.err.println( "Unexpected exception:" + e.getMessage() );
			    e.printStackTrace();
			}
		}
	}

	public static class Ingest {
	
	    public static void main( String[] args ) {
	        final CommandLineParser parser = new DefaultParser();

            // create the Options
            final Options options = new Options();
            options.addOption( Option.builder( "b" )
                    .longOpt( "bundle" )
                    .desc( "The data bundle to ingest." )
                    .required()
                    .hasArg()
                    .argName( "BUNDLE-NAME" )
                    .build() );
            options.addOption( Option.builder()
                    .longOpt( "show-progress" )
                    .desc( "Print progress information to the terminal." )
                    .hasArg( false )
                    .build() );
            options.addOption( Option.builder()
                    .longOpt( "no-show-progress" )
                    .desc( "Hide progress information." )
                    .hasArg( false )
                    .build() );
            
            try {
                final CommandLine line = parser.parse( options, args );
                final String bundle = line.getOptionValue( 'b', "quantopian-quandl" );
                final boolean showProgress = line.hasOption( "show-progress" ) && !line.hasOption( "no-show-progress" );
                //Ingest the data for the given bundle.
                Bundles.ingest( bundle, System.getenv(), LocalDateTime.now(), showProgress );
            }
            catch( ParseException e ) {
                System.err.println( "Unexpected exception:" + e.getMessage() );
                e.printStackTrace();
            }
	    }
	}
	
	
	
//	@main.command()
//	@click.option(
//	    '-b',
//	    '--bundle',
//	    default='quantopian-quandl',
//	    metavar='BUNDLE-NAME',
//	    show_default=True,
//	    help='The data bundle to clean.',
//	)
//	@click.option(
//	    '-b',
//	    '--before',
//	    type=Timestamp(),
//	    help='Clear all data before TIMESTAMP.'
//	    ' This may not be passed with -k / --keep-last',
//	)
//	@click.option(
//	    '-a',
//	    '--after',
//	    type=Timestamp(),
//	    help='Clear all data after TIMESTAMP'
//	    ' This may not be passed with -k / --keep-last',
//	)
//	@click.option(
//	    '-k',
//	    '--keep-last',
//	    type=int,
//	    metavar='N',
//	    help='Clear all but the last N downloads.'
//	    ' This may not be passed with -b / --before or -a / --after',
//	)
//	def clean(bundle, before, after, keep_last):
//	    """Clean up data downloaded with the ingest command.
//	    """
//	    Bundles.clean(
//	        bundle,
//	        before,
//	        after,
//	        keep_last,
//	    )
//	
//	

	public static class BundlesCommand {
//	    """List all of the available data bundles.
//	    """
//	    for bundle in sorted(Bundles.bundles.keys()):
//	        try:
//	            ingestions = sorted(
//	                (str(Bundles.from_bundle_ingest_dirname(ing))
//	                 for ing in os.listdir(Paths.data_path([bundle]))
//	                 if not Paths.hidden(ing)),
//	                reverse=True,
//	            )
//	        except OSError as e:
//	            if e.errno != errno.ENOENT:
//	                raise
//	            ingestions = []
//	
//	        # If we got no ingestions, either because the directory didn't exist or
//	        # because there were no entries, print a single message indicating that
//	        # no ingestions have yet been made.
//	        for timestamp in ingestions or ["<no ingestions>"]:
//	            print("%s %s" % (bundle, timestamp))
	}
}


    
/*----------------------------------------------------------------------------------------------------------

 
import errno
import os
from functools import wraps

import click
import logbook
import pandas as pd

from zipline.data import bundles as bundles_module
from zipline.utils.cli import Date, Timestamp
import zipline.utils.paths as pth
from zipline.utils.run_algo import _run, load_extensions

try:
    __IPYTHON__
except NameError:
    __IPYTHON__ = False


@click.group()
@click.option(
    '-e',
    '--extension',
    multiple=True,
    help='File or module path to a zipline extension to load.',
)
@click.option(
    '--strict-extensions/--non-strict-extensions',
    is_flag=True,
    help='If --strict-extensions is passed then zipline will not run if it'
    ' cannot load all of the specified extensions. If this is not passed or'
    ' --non-strict-extensions is passed then the failure will be logged but'
    ' execution will continue.',
)
@click.option(
    '--default-extension/--no-default-extension',
    is_flag=True,
    default=True,
    help="Don't load the default zipline extension.py file in $ZIPLINE_HOME.",
)
def main(extension, strict_extensions, default_extension):
    """Top level zipline entry point.
    """
    # install a logbook handler before performing any other operations
    logbook.StderrHandler().push_application()
    load_extensions(
        default_extension,
        extension,
        strict_extensions,
        os.environ,
    )


def extract_option_object(option):
    """Convert a click.option call into a click.Option object.

    Parameters
    ----------
    option : decorator
        A click.option decorator.

    Returns
    -------
    option_object : click.Option
        The option object that this decorator will create.
    """
    @option
    def opt():
        pass

    return opt.__click_params__[0]


def ipython_only(option):
    """Mark that an option should only be exposed in IPython.

    Parameters
    ----------
    option : decorator
        A click.option decorator.

    Returns
    -------
    ipython_only_dec : decorator
        A decorator that correctly applies the argument even when not
        using IPython mode.
    """
    if __IPYTHON__:
        return option

    argname = extract_option_object(option).name

    def d(f):
        @wraps(f)
        def _(*args, **kwargs):
            kwargs[argname] = None
            return f(*args, **kwargs)
        return _
    return d


@main.command()
@click.option(
    '-f',
    '--algofile',
    default=None,
    type=click.File('r'),
    help='The file that contains the algorithm to run.',
)
@click.option(
    '-t',
    '--algotext',
    help='The algorithm script to run.',
)
@click.option(
    '-D',
    '--define',
    multiple=True,
    help="Define a name to be bound in the namespace before executing"
    " the algotext. For example '-Dname=value'. The value may be any python"
    " expression. These are evaluated in order so they may refer to previously"
    " defined names.",
)
@click.option(
    '--data-frequency',
    type=click.Choice({'daily', 'minute'}),
    default='daily',
    show_default=True,
    help='The data frequency of the simulation.',
)
@click.option(
    '--capital-base',
    type=float,
    default=10e6,
    show_default=True,
    help='The starting capital for the simulation.',
)
@click.option(
    '-b',
    '--bundle',
    default='quantopian-quandl',
    metavar='BUNDLE-NAME',
    show_default=True,
    help='The data bundle to use for the simulation.',
)
@click.option(
    '--bundle-timestamp',
    type=Timestamp(),
    default=pd.Timestamp.utcnow(),
    show_default=False,
    help='The date to lookup data on or before.\n'
    '[default: <current-time>]'
)
@click.option(
    '-s',
    '--start',
    type=Date(tz='utc', as_timestamp=True),
    help='The start date of the simulation.',
)
@click.option(
    '-e',
    '--end',
    type=Date(tz='utc', as_timestamp=True),
    help='The end date of the simulation.',
)
@click.option(
    '-o',
    '--output',
    default='-',
    metavar='FILENAME',
    show_default=True,
    help="The location to write the perf data. If this is '-' the perf will"
    " be written to stdout.",
)
@click.option(
    '--print-algo/--no-print-algo',
    is_flag=True,
    default=False,
    help='Print the algorithm to stdout.',
)
@ipython_only(click.option(
    '--local-namespace/--no-local-namespace',
    is_flag=True,
    default=None,
    help='Should the algorithm methods be resolved in the local namespace.'
))
@click.pass_context
def run(ctx,
        algofile,
        algotext,
        define,
        data_frequency,
        capital_base,
        bundle,
        bundle_timestamp,
        start,
        end,
        output,
        print_algo,
        local_namespace):
    """Run a backtest for the given algorithm.
    """
    # check that the start and end dates are passed correctly
    if start is None and end is None:
        # check both at the same time to avoid the case where a user
        # does not pass either of these and then passes the first only
        # to be told they need to pass the second argument also
        ctx.fail(
            "must specify dates with '-s' / '--start' and '-e' / '--end'",
        )
    if start is None:
        ctx.fail("must specify a start date with '-s' / '--start'")
    if end is None:
        ctx.fail("must specify an end date with '-s' / '--end'")

    if (algotext is not None) == (algofile is not None):
        ctx.fail(
            "must specify exactly one of '-f' / '--algofile' or"
            " '-t' / '--algotext'",
        )

    perf = _run(
        initialize=None,
        handle_data=None,
        before_trading_start=None,
        analyze=None,
        algofile=algofile,
        algotext=algotext,
        defines=define,
        data_frequency=data_frequency,
        capital_base=capital_base,
        data=None,
        bundle=bundle,
        bundle_timestamp=bundle_timestamp,
        start=start,
        end=end,
        output=output,
        print_algo=print_algo,
        local_namespace=local_namespace,
        environ=os.environ,
    )

    if output == '-':
        click.echo(str(perf))
    elif output != os.devnull:  # make the zipline magic not write any data
        perf.to_pickle(output)

    return perf


def zipline_magic(line, cell=None):
    """The zipline IPython cell magic.
    """
    try:
        return run.main(
            # put our overrides at the start of the parameter list so that
            # users may pass values with higher precedence
            [
                '--algotext', cell,
                '--output', os.devnull,  # don't write the results by default
            ] + ([
                # these options are set when running in line magic mode
                # set a non None algo text to use the ipython user_ns
                '--algotext', '',
                '--local-namespace',
            ] if cell is None else []) + line.split(),
            '%s%%zipline' % ((cell or '') and '%'),
            # don't use system exit and propogate errors to the caller
            standalone_mode=False,
        )
    except SystemExit as e:
        # https://github.com/mitsuhiko/click/pull/533
        # even in standalone_mode=False `--help` really wants to kill us ;_;
        if e.code:
            raise ValueError('main returned non-zero status code: %d' % e.code)


@main.command()
@click.option(
    '-b',
    '--bundle',
    default='quantopian-quandl',
    metavar='BUNDLE-NAME',
    show_default=True,
    help='The data bundle to ingest.',
)
@click.option(
    '--show-progress/--no-show-progress',
    is_flag=True,
    default=True,
    help='Print progress information to the terminal.'
)
def ingest(bundle, show_progress):
    """Ingest the data for the given bundle.
    """
    bundles_module.ingest(
        bundle,
        os.environ,
        pd.Timestamp.utcnow(),
        show_progress,
    )


@main.command()
@click.option(
    '-b',
    '--bundle',
    default='quantopian-quandl',
    metavar='BUNDLE-NAME',
    show_default=True,
    help='The data bundle to clean.',
)
@click.option(
    '-b',
    '--before',
    type=Timestamp(),
    help='Clear all data before TIMESTAMP.'
    ' This may not be passed with -k / --keep-last',
)
@click.option(
    '-a',
    '--after',
    type=Timestamp(),
    help='Clear all data after TIMESTAMP'
    ' This may not be passed with -k / --keep-last',
)
@click.option(
    '-k',
    '--keep-last',
    type=int,
    metavar='N',
    help='Clear all but the last N downloads.'
    ' This may not be passed with -b / --before or -a / --after',
)
def clean(bundle, before, after, keep_last):
    """Clean up data downloaded with the ingest command.
    """
    bundles_module.clean(
        bundle,
        before,
        after,
        keep_last,
    )


@main.command()
def bundles():
    """List all of the available data bundles.
    """
    for bundle in sorted(bundles_module.bundles.keys()):
        try:
            ingestions = sorted(
                (str(bundles_module.from_bundle_ingest_dirname(ing))
                 for ing in os.listdir(pth.data_path([bundle]))
                 if not pth.hidden(ing)),
                reverse=True,
            )
        except OSError as e:
            if e.errno != errno.ENOENT:
                raise
            ingestions = []

        # If we got no ingestions, either because the directory didn't exist or
        # because there were no entries, print a single message indicating that
        # no ingestions have yet been made.
        for timestamp in ingestions or ["<no ingestions>"]:
            print("%s %s" % (bundle, timestamp))


if __name__ == '__main__':
    main()
*/


/*
__init__.py

from . import data
from . import finance
from . import gens
from . import utils
from .utils.run_algo import run_algorithm
from ._version import get_versions
# These need to happen after the other imports.
from . algorithm import TradingAlgorithm
from . import api

__version__ = get_versions()['version']
del get_versions


def load_ipython_extension(ipython):
    from .__main__ import zipline_magic
    ipython.register_magic_function(zipline_magic, 'line_cell', 'zipline')


__all__ = [
    'TradingAlgorithm',
    'api',
    'data',
    'finance',
    'gens',
    'run_algorithm',
    'utils',
]
*/
